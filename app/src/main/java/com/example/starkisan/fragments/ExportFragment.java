package com.example.starkisan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.example.starkisan.R;
import com.example.starkisan.CSVWriter;
import com.example.starkisan.models.CommodityEntry;
import com.example.starkisan.models.UserModel;
import com.farbod.labelledspinner.LabelledSpinner;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExportFragment extends Fragment {
    private static final String EXPORT_CHOICE_ALL = "Export All";
    private static final String EXPORT_CHOICE_BY_DATE = "By Date";
    private static final int LOADER_ID = 0;
    /* access modifiers changed from: private */
    public String choiceData;
    /* access modifiers changed from: private */
    public String csv;
    private DatePicker datePicker;
    private Button fetchBtn;
    private FloatingActionButton goBtn;
    /* access modifiers changed from: private */
    public TextInputEditText input_export_email;
    /* access modifiers changed from: private */
    public LabelledSpinner mExportSpinner;
    private OnClickListener onFetchClick = new OnClickListener() {
        public void onClick(View view) {
            ExportFragment.this.fetchEntries();
        }
    };
    private OnClickListener onGoBtnClick = new OnClickListener() {
        public void onClick(View v) {
            Intent emailIntent = new Intent("android.intent.action.SEND");
            emailIntent.setType("text/plain");
            emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{ExportFragment.this.input_export_email.getText().toString()});
            emailIntent.putExtra("android.intent.extra.SUBJECT", ExportFragment.this.choiceData);
            StringBuilder sb = new StringBuilder();
            sb.append("Data from ");
            sb.append(ExportFragment.this.choiceData);
            emailIntent.putExtra("android.intent.extra.TEXT", sb.toString());
            emailIntent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(ExportFragment.this.getContext(), "com.example.starkisan.fileprovider", new File(ExportFragment.this.csv)));
            ExportFragment.this.startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
        }
    };

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mExportSpinner = (LabelledSpinner) view.findViewById(R.id.spinnerExportChoice);
        this.datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        this.goBtn = (FloatingActionButton) view.findViewById(R.id.goBtn);
        this.input_export_email = (TextInputEditText) view.findViewById(R.id.input_export_email_id);
        this.fetchBtn = (Button) view.findViewById(R.id.fetchBtn);
        this.datePicker.setVisibility(0);
        int day = this.datePicker.getDayOfMonth();
        int month = this.datePicker.getMonth();
        int year = this.datePicker.getYear();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        this.choiceData = this.mExportSpinner.getSpinner().getSelectedItem().equals(EXPORT_CHOICE_BY_DATE) ? SDF.format(calendar.getTime()) : "alldata";
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getExternalFilesDir(null).getAbsolutePath());
        sb.append("/data.csv");
        this.csv = sb.toString();
        this.goBtn.setOnClickListener(this.onGoBtnClick);
        this.fetchBtn.setOnClickListener(this.onFetchClick);
    }

    public static ExportFragment newInstance() {
        return new ExportFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.export_fragment, container, false);
    }


    /* access modifiers changed from: private */
    public void fetchEntries() {
        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            /* JADX WARNING: Code restructure failed: missing block: B:10:0x0046, code lost:
                if (r1.equals(com.example.starkisan.fragments.ExportFragment.EXPORT_CHOICE_ALL) != false) goto L_0x004a;
             */
            /* JADX WARNING: Removed duplicated region for block: B:13:0x004c  */
            /* JADX WARNING: Removed duplicated region for block: B:15:0x0055  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onSuccess(com.google.firebase.firestore.DocumentSnapshot r8) {
                /*
                    r7 = this;
                    java.lang.Class<com.example.starkisan.models.UserModel> r0 = com.example.starkisan.models.UserModel.class
                    java.lang.Object r0 = r8.toObject(r0)
                    com.example.starkisan.models.UserModel r0 = (com.example.starkisan.models.UserModel) r0
                    java.lang.Boolean r1 = r0.getAdmin()
                    boolean r1 = r1.booleanValue()
                    r2 = 0
                    if (r1 == 0) goto L_0x005c
                    com.example.starkisan.fragments.ExportFragment r1 = com.example.starkisan.fragments.ExportFragment.this
                    com.farbod.labelledspinner.LabelledSpinner r1 = r1.mExportSpinner
                    android.widget.Spinner r1 = r1.getSpinner()
                    java.lang.Object r1 = r1.getSelectedItem()
                    java.lang.String r1 = r1.toString()
                    r3 = -1
                    int r4 = r1.hashCode()
                    r5 = -2093907147(0xffffffff83318335, float:-5.2166243E-37)
                    r6 = 1
                    if (r4 == r5) goto L_0x0040
                    r2 = 1941503447(0x73b8fdd7, float:2.9313083E31)
                    if (r4 == r2) goto L_0x0036
                L_0x0035:
                    goto L_0x0049
                L_0x0036:
                    java.lang.String r2 = "By Date"
                    boolean r1 = r1.equals(r2)
                    if (r1 == 0) goto L_0x0035
                    r2 = r6
                    goto L_0x004a
                L_0x0040:
                    java.lang.String r4 = "Export All"
                    boolean r1 = r1.equals(r4)
                    if (r1 == 0) goto L_0x0035
                    goto L_0x004a
                L_0x0049:
                    r2 = r3
                L_0x004a:
                    if (r2 == 0) goto L_0x0055
                    if (r2 == r6) goto L_0x004f
                    goto L_0x005b
                L_0x004f:
                    com.example.starkisan.fragments.ExportFragment r1 = com.example.starkisan.fragments.ExportFragment.this
                    r1.fetchByDate()
                    goto L_0x005b
                L_0x0055:
                    com.example.starkisan.fragments.ExportFragment r1 = com.example.starkisan.fragments.ExportFragment.this
                    r1.fetchAllEntries()
                L_0x005b:
                    goto L_0x006b
                L_0x005c:
                    com.example.starkisan.fragments.ExportFragment r1 = com.example.starkisan.fragments.ExportFragment.this
                    android.content.Context r1 = r1.getContext()
                    java.lang.String r3 = "You do not have access!"
                    android.widget.Toast r1 = android.widget.Toast.makeText(r1, r3, r2)
                    r1.show()
                L_0x006b:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.example.starkisan.fragments.ExportFragment.C09293.onSuccess(com.google.firebase.firestore.DocumentSnapshot):void");
            }
        });
    }

    /* access modifiers changed from: private */
    public void fetchByDate() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (UserModel user : queryDocumentSnapshots.toObjects(UserModel.class)) {
                    String str = "dateTime";
                    db.collection(user.getUid()).whereEqualTo(str, (Object) ExportFragment.this.choiceData).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            ExportFragment.this.exportAll(queryDocumentSnapshots.toObjects(CommodityEntry.class));
                            Toast.makeText(ExportFragment.this.getContext(), "Fetched entries by date!", 0).show();
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void fetchAllEntries() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (UserModel user : queryDocumentSnapshots.toObjects(UserModel.class)) {
                    db.collection(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            ExportFragment.this.exportAll(queryDocumentSnapshots.toObjects(CommodityEntry.class));
                            Toast.makeText(ExportFragment.this.getContext(), "Fetched all entries!", 0).show();
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void exportAll(List<CommodityEntry> data) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(this.csv));
            for (CommodityEntry e : data) {
                writer.writeNext(e.String());
            }
            writer.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
