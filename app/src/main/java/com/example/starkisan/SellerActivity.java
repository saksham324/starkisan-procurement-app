package com.example.starkisan;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.starkisan.adapters.RowAdapter;
import com.example.starkisan.models.PreferenceModel;
import com.example.starkisan.models.RowModel;
import com.example.starkisan.models.SellerEntry;
import com.farbod.labelledspinner.LabelledSpinner;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class SellerActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public RowAdapter adapter;
    ArrayList<String> checked;
    TextInputEditText comments;
    ListView commodityListview;

    /* renamed from: db */
    private FirebaseFirestore f100db;
    private FirebaseUser firebaseUser;
    LabelledSpinner mandiSpinner;
    private OnClickListener onSaveBtnClick = new OnClickListener() {
        public void onClick(View view) {
            if (SellerActivity.this.sellerName.getText().toString().equals("")) {
                Toast.makeText(SellerActivity.this, "Seller Name is required!", 0).show();
                return;
            }
            SellerActivity.this.getCheckedItems();
            SellerActivity.this.saveEntry();
            SellerActivity.this.sellerName.getText().clear();
            SellerActivity.this.sellerNumber.getText().clear();
            SellerActivity.this.comments.getText().clear();
            SellerActivity.this.startActivity(new Intent(SellerActivity.this, SettingsActivity.class));
        }
    };
    ArrayList<RowModel> rows;
    Button saveBtn;
    ScrollView scrollView;
    TextInputEditText sellerName;
    TextInputEditText sellerNumber;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_seller);
        this.commodityListview = (ListView) findViewById(R.id.commodityListview);
        this.scrollView = (ScrollView) findViewById(R.id.sellersScrollView);
        this.mandiSpinner = (LabelledSpinner) findViewById(R.id.mandiSpinner);
        this.sellerName = (TextInputEditText) findViewById(R.id.sellerName);
        this.sellerNumber = (TextInputEditText) findViewById(R.id.sellerNumber);
        this.comments = (TextInputEditText) findViewById(R.id.sellerComments);
        this.saveBtn = (Button) findViewById(R.id.savesellerbtn);
        this.saveBtn.setOnClickListener(this.onSaveBtnClick);
        this.rows = new ArrayList<>();
        this.checked = new ArrayList<>();
        this.commodityListview.setChoiceMode(2);
        this.commodityListview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((RowModel) SellerActivity.this.commodityListview.getItemAtPosition(i)).setChecked(Boolean.valueOf(!((CheckBox) view.findViewById(R.id.checkBox)).isChecked()));
                SellerActivity.this.adapter.notifyDataSetChanged();
            }
        });
        this.commodityListview.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SellerActivity.this.scrollView.requestDisallowInterceptTouchEvent(true);
                if (motionEvent.getActionMasked() == 1) {
                    SellerActivity.this.scrollView.requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        this.f100db = FirebaseFirestore.getInstance();
        this.f100db.collection("preference_data").document("lists").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                PreferenceModel lists = (PreferenceModel) documentSnapshot.toObject(PreferenceModel.class);
                SellerActivity.this.mandiSpinner.setCustomAdapter(new ArrayAdapter<>(SellerActivity.this.getApplicationContext(), 17367049, lists.getSourceList()));
                for (String commodity : lists.getCommodityList()) {
                    SellerActivity.this.rows.add(new RowModel(commodity, Boolean.valueOf(false)));
                }
            }
        });
        this.adapter = new RowAdapter(getApplicationContext(), this.rows);
        this.commodityListview.setAdapter(this.adapter);
    }

    /* access modifiers changed from: private */
    public void saveEntry() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "User hasn't logged in", 0).show();
            finish();
            return;
        }
        final SellerEntry entry = new SellerEntry();
        entry.setMandi(this.mandiSpinner.getSpinner().getSelectedItem().toString());
        entry.setName(this.sellerName.getText().toString());
        entry.setPhone(this.sellerNumber.getText().toString());
        entry.setComments(this.comments.getText().toString());
        entry.setCommodities(getCheckedItems());
        db.collection("sellers").add(entry).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(SellerActivity.this, "New Seller Added!", 0).show();
                String id = documentReference.getId();
                entry.setId(id);
                db.collection("sellers").document(id).set(entry);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Toast.makeText(SellerActivity.this, "Error Adding New Seller", 0).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public List<String> getCheckedItems() {
        ArrayList<String> checkedList = new ArrayList<>();
        SparseBooleanArray sp = this.commodityListview.getCheckedItemPositions();
        for (int i = 0; i < sp.size(); i++) {
            if (sp.valueAt(i)) {
                checkedList.add(((RowModel) this.commodityListview.getItemAtPosition(i)).getText());
            }
        }
        return checkedList;
    }
}
