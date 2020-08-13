package com.example.starkisan.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.example.starkisan.R;
import com.example.starkisan.models.CommodityEntry;
import com.example.starkisan.models.PreferenceModel;
import com.example.starkisan.models.SellerEntry;
import com.example.starkisan.models.UserModel;
import com.farbod.labelledspinner.LabelledSpinner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StartFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_CODE = 0;
    private static final int REQUEST_CODE_TAKE_FROM_CAMERA = 1;
    private static final int REQUEST_CODE_TAKE_FROM_GALLERY = 1;
    public static final String TAG = "fragment_start";
    private int clicks = 0;
    private String currentPhotoPath;

    public FirebaseFirestore db;
    private Uri downloadUri;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Bitmap imageBitmap;
    private ImageView imageView;
    private LabelledSpinner mCommoditySpinner;
    private LabelledSpinner mGradeSpinner;
    private Uri mImageUri;
    private LabelledSpinner mMandiSpinner;
    private EditText mRate;
    private EditText mRemarks;
    private LabelledSpinner mSellerSpinner;
    private Button photoBtn;
    FirebaseStorage storage;
    StorageReference storageReference;
    ArrayAdapter<String> sellerAdapter;

    private OnClickListener onPhotoBtnClick = new OnClickListener() {
        public void onClick(View view) {
            dispatchTakePictureIntent();
        }
    };

    private OnClickListener onPlayBtnClick = new OnClickListener() {
        public void onClick(View v) {
            if (mRate.getText().toString().equals("")) {
                Toast.makeText(StartFragment.this.getContext(), "Rate is required!", Toast.LENGTH_SHORT).show();
                return;
            }
            saveEntry();
//            mCommoditySpinner.setSelection(StartFragment.access$204(StartFragment.this) % StartFragment.this.getResources().getStringArray(R.array.items_spinner_commodity).length);
            mRate.getText().clear();
            mRemarks.getText().clear();
            mImageUri = null;
            imageBitmap = null;
            downloadUri = null;
//            imageView.setImageResource(17170445); // empty image
        }
    };


    public static StartFragment newInstance() {
        return new StartFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mCommoditySpinner = (LabelledSpinner) view.findViewById(R.id.spinnerCommodity);
        this.mGradeSpinner = (LabelledSpinner) view.findViewById(R.id.spinnerGrade);
        this.mMandiSpinner = (LabelledSpinner) view.findViewById(R.id.spinnerMandi);
        this.mSellerSpinner = (LabelledSpinner) view.findViewById(R.id.spinnerSeller);
        this.mRate = (EditText) view.findViewById(R.id.input_rate);
        this.mRemarks = (EditText) view.findViewById(R.id.input_remarks);
        this.photoBtn = (Button) view.findViewById(R.id.start_upload_photo_btn);
        this.imageView = (ImageView) view.findViewById(R.id.imageview);
        ((FloatingActionButton) view.findViewById(R.id.playBtn)).setOnClickListener(this.onPlayBtnClick);
        this.photoBtn.setOnClickListener(this.onPhotoBtnClick);
        fetchSpinnerData();
        checkPermissions();
    }

    /* access modifiers changed from: private */
    public void saveEntry() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        if (firebaseUser == null || db == null) {
            Toast.makeText(getContext(), "Error saving", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }

        final String uid = firebaseUser.getUid();

        final CommodityEntry newEntry = new CommodityEntry();
        newEntry.setId(db.collection(uid).document().getId());
        newEntry.setMandiName(this.mMandiSpinner.getSpinner().getSelectedItem().toString());
        newEntry.setmCommodity(this.mCommoditySpinner.getSpinner().getSelectedItem().toString());
        newEntry.setmGradeType(this.mGradeSpinner.getSpinner().getSelectedItem().toString());
        newEntry.setmRate(Double.valueOf(Double.parseDouble(this.mRate.getText().toString())));
        newEntry.setmRemarks(this.mRemarks.getText().toString());
        newEntry.setSellerName(this.mSellerSpinner.getSpinner().getSelectedItem().toString());
        newEntry.setDateTime(new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date()));

        Uri uri = downloadUri;

        if (uri != null) {
            newEntry.setImageUrl(uri.toString());
        } else {
            newEntry.setImageUrl("No Image Uploaded");
        }

        db.collection("users").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                newEntry.setmUserName(((UserModel) documentSnapshot.toObject(UserModel.class)).getName());
            }
        });
        db.collection(uid).add(newEntry).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(StartFragment.this.getContext(), "Saved", Toast.LENGTH_SHORT).show();
                String id = documentReference.getId();
                newEntry.setId(id);
                db.collection(uid).document(id).set(newEntry);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Toast.makeText(StartFragment.this.getContext(), "Error Saving Data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
            }
            if (photoFile != null) {
                mImageUri = FileProvider.getUriForFile(getContext(), "com.example.starkisan.fileprovider", photoFile);
                takePictureIntent.putExtra("output", mImageUri);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(timeStamp);
        sb.append("_");
        File image = File.createTempFile(sb.toString(), ".jpg", getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        this.currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && requestCode == 1 && resultCode == -1) {
            try {
                this.imageBitmap = Media.getBitmap(getActivity().getContentResolver(), this.mImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mImageUri = getImageUri(getContext(), this.imageBitmap);
            if (this.mImageUri != null) {
                Bitmap bitmap = this.imageBitmap;
                if (bitmap != null) {
                    this.imageView.setImageBitmap(bitmap);
                    uploadImage();
                }
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        inImage.compress(CompressFormat.JPEG, 50, new ByteArrayOutputStream());
        return Uri.parse(Media.insertImage(inContext.getContentResolver(), inImage, "Title", null));
    }

    private void uploadImage() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateTimeFormat.format(new Date());
        String currentDate = dateFormat.format(new Date());

        String filename = "images/" + mMandiSpinner.getSpinner().getSelectedItem().toString().split("/")[0] + "_"
                + mCommoditySpinner.getSpinner().getSelectedItem().toString() + "_"
                + mGradeSpinner.getSpinner().getSelectedItem().toString() + "_"
                + currentDateTime + ".jpg";

        if (mImageUri != null) {
            final StorageReference ref = storageReference.child(filename);
            ref.putFile(mImageUri)
                    .addOnSuccessListener((OnSuccessListener) new OnSuccessListener<TaskSnapshot>() {
                public void onSuccess(TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        public void onSuccess(Uri downloadUrl) {
                            downloadUri = downloadUrl;
                        }
                    });
                }
            }).addOnFailureListener((OnFailureListener) new OnFailureListener() {
                public void onFailure(Exception e) {
                    Context context = getContext();
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void checkPermissions() {
        if (VERSION.SDK_INT >= 23) {
            String str = "android.permission.WRITE_EXTERNAL_STORAGE";
            String str2 = "android.permission.CAMERA";
            if (!(ActivityCompat.checkSelfPermission(getContext(), str) == 0 && ActivityCompat.checkSelfPermission(getContext(), str2) == 0)) {
                requestPermissions(new String[]{str, str2}, 0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((grantResults[0] == 0 && grantResults[1] == 0) || VERSION.SDK_INT < 23) {
            return;
        }
        if (shouldShowRequestPermissionRationale("android.permission.WRITE_EXTERNAL_STORAGE") || shouldShowRequestPermissionRationale("android.permission.CAMERA")) {
            Builder builder = new Builder(getContext());
            builder.setMessage((CharSequence) "These permissions are needed to set a profile picture.").setTitle((CharSequence) "Permissions required");
            builder.setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    StartFragment.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, 0);
                }
            });
            builder.show();
        }
    }

    private void fetchSpinnerData() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection("preference_data").document("lists").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                PreferenceModel lists = (PreferenceModel) documentSnapshot.toObject(PreferenceModel.class);
                final List<String> mandiList = lists.getSourceList();
                final List<String> gradeList = lists.getGradeList();
                final List<String> commodityList = lists.getCommodityList();
                mMandiSpinner.setCustomAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, mandiList));
                mGradeSpinner.setCustomAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, gradeList));
                mCommoditySpinner.setCustomAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, commodityList));
                db.collection("sellers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<SellerEntry> sellerEntries = queryDocumentSnapshots.toObjects(SellerEntry.class);
                        List<String> sellerList = new ArrayList<>();
                        for (SellerEntry seller : sellerEntries) {
                            if (seller.getMandi().equals(mMandiSpinner.getSpinner().getSelectedItem().toString()) && seller.getCommodities().contains(mCommoditySpinner.getSpinner().getSelectedItem().toString())) {
                                sellerList.add(seller.getName());
                            }
                        }
                        sellerList.add("Other");
                        sellerAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, sellerList);
                        mSellerSpinner.setCustomAdapter(sellerAdapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(Exception e) {
                        List<String> sellerList = new ArrayList<>();
                        sellerList.add("Other");
                        sellerAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, sellerList);
                        mSellerSpinner.setCustomAdapter(sellerAdapter);
                    }
                });
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        db.collection("preference_data").document("lists").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                db.collection("sellers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<SellerEntry> sellerEntries = queryDocumentSnapshots.toObjects(SellerEntry.class);
                        List<String> sellerList = new ArrayList<>();
                        for (SellerEntry seller : sellerEntries) {
                            if (seller.getMandi().equals(mMandiSpinner.getSpinner().getSelectedItem().toString()) && seller.getCommodities().contains(mCommoditySpinner.getSpinner().getSelectedItem().toString())) {
                                sellerList.add(seller.getName());
                            }
                        }
                        sellerList.add("Other");
                        sellerAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, sellerList);
                        mSellerSpinner.setCustomAdapter(sellerAdapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    public void onFailure(Exception e) {
                        List<String> sellerList = new ArrayList<>();
                        sellerList.add("Other");
                        sellerAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, sellerList);
                        mSellerSpinner.setCustomAdapter(sellerAdapter);
                    }
                });
            }
        });
    }
}
