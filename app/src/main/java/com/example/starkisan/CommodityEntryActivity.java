package com.example.starkisan;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.starkisan.adapters.CommodityEntryAdapter;
import com.example.starkisan.adapters.ItemEntryAdapter;
import com.example.starkisan.models.CommodityEntry;
import com.example.starkisan.models.ItemModel;
import com.farbod.labelledspinner.LabelledSpinner;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.net.HttpHeaders;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;

public class CommodityEntryActivity extends AppCompatActivity {
    private static final String ARGS_ID_KEY = "args_id_key";
    public static final String INTENT_FROM = "intent_from";
    private static final int LOADER_ID = 1;
    private static final String SAVED_ID = "saved_id";
    private static final String SAVED_INTENT_FROM = "saved_intent_from";
    private static final String SAVED_LIST = "saved_models";
    public static final String TAG = "commodity_entry_activity";
    public OnClickListener commentDialogHandler = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            if (which == -2) {
                dialog.cancel();
                return;
            }
            ((ItemModel) CommodityEntryActivity.this.mAdapter.getItem(4)).setData(((EditText) ((AlertDialog) dialog).findViewById(R.id.commentEditText)).getText().toString());
            CommodityEntryActivity.this.mAdapter.notifyDataSetChanged();
        }
    };
    public OnClickListener commodityDialogHandler = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            if (which == -2) {
                dialog.cancel();
                return;
            }
            ((ItemModel) CommodityEntryActivity.this.mAdapter.getItem(1)).setData(((LabelledSpinner) ((AlertDialog) dialog).findViewById(R.id.dialogCommoditySpinner)).getSpinner().getSelectedItem().toString());
            CommodityEntryActivity.this.mAdapter.notifyDataSetChanged();
        }
    };

    /* renamed from: db */
    private FirebaseFirestore f77db;
    public OnClickListener gradeDialogHandler = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            if (which == -2) {
                dialog.cancel();
                return;
            }
            ((ItemModel) CommodityEntryActivity.this.mAdapter.getItem(2)).setData(((LabelledSpinner) ((AlertDialog) dialog).findViewById(R.id.dialogGradeSpinner)).getSpinner().getSelectedItem().toString());
            CommodityEntryActivity.this.mAdapter.notifyDataSetChanged();
        }
    };
    public OnClickListener imageurlDialogHandler = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            if (which == -2) {
                dialog.cancel();
                return;
            }
            CommodityEntryActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(((ItemModel) CommodityEntryActivity.this.mAdapter.getItem(6)).getData())));
        }
    };
    /* access modifiers changed from: private */
    public ItemEntryAdapter mAdapter;
    private FirebaseAuth mAuth;
    private String mEntryId;
    /* access modifiers changed from: private */
    public ArrayList<ItemModel> mItems;
    private ListView mListView;
    public OnClickListener mandiDialogHandler = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            if (which == -2) {
                dialog.cancel();
                return;
            }
            ((ItemModel) CommodityEntryActivity.this.mAdapter.getItem(0)).setData(((LabelledSpinner) ((AlertDialog) dialog).findViewById(R.id.dialogSpinner)).getSpinner().getSelectedItem().toString());
            CommodityEntryActivity.this.mAdapter.notifyDataSetChanged();
        }
    };
    public OnClickListener numberDialogHandler = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            if (which == -2) {
                dialog.cancel();
                return;
            }
            ((ItemModel) CommodityEntryActivity.this.mAdapter.getItem(3)).setData(((EditText) ((AlertDialog) dialog).findViewById(R.id.numberEditText)).getText().toString());
            CommodityEntryActivity.this.mAdapter.notifyDataSetChanged();
        }
    };
    public OnDateSetListener onDatePick = new OnDateSetListener() {
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            ItemModel itemModel = (ItemModel) CommodityEntryActivity.this.mAdapter.getItem(5);
            StringBuilder sb = new StringBuilder();
            sb.append(year);
            String str = "-";
            sb.append(str);
            sb.append(month + 1);
            sb.append(str);
            sb.append(dayOfMonth);
            itemModel.setData(sb.toString());
            CommodityEntryActivity.this.mAdapter.notifyDataSetChanged();
        }
    };
    private View.OnClickListener onDeleteBtnClick = new View.OnClickListener() {
        public void onClick(View v) {
            CommodityEntryActivity.this.deleteEntry();
        }
    };
    private View.OnClickListener onUpdateBtnClick = new View.OnClickListener() {
        public void onClick(View v) {
            CommodityEntryActivity.this.updateEntry();
        }
    };
    public OnClickListener sellerDialogHandler = new OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            if (which == -2) {
                dialog.cancel();
                return;
            }
            ((ItemModel) CommodityEntryActivity.this.mAdapter.getItem(7)).setData(((EditText) ((AlertDialog) dialog).findViewById(R.id.sellerEditText)).getText().toString());
            CommodityEntryActivity.this.mAdapter.notifyDataSetChanged();
        }
    };
    private FirebaseUser user;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.content_commodity_entry);
        this.mAuth = FirebaseAuth.getInstance();
        this.user = this.mAuth.getCurrentUser();
        this.f77db = FirebaseFirestore.getInstance();
        this.mListView = (ListView) findViewById(R.id.listView);
        this.mItems = null;
        if (savedInstanceState != null) {
            this.mItems = savedInstanceState.getParcelableArrayList(SAVED_LIST);
            this.mEntryId = savedInstanceState.getString(SAVED_ID);
            setupAdapter();
        } else {
            this.mItems = new ArrayList<>();
        }
        setFields();
        Log.d(TAG, "Created");
        FloatingActionButton deleteBtn = (FloatingActionButton) findViewById(R.id.deleteBtn);
        ((FloatingActionButton) findViewById(R.id.updateBtn)).setOnClickListener(this.onUpdateBtnClick);
        deleteBtn.setOnClickListener(this.onDeleteBtnClick);
    }

    /* access modifiers changed from: private */
    public void updateEntry() {
        CommodityEntry newEntry = new CommodityEntry();
        newEntry.setMandiName(((ItemModel) this.mAdapter.getItem(0)).getData());
        newEntry.setmCommodity(((ItemModel) this.mAdapter.getItem(1)).getData());
        newEntry.setmGradeType(((ItemModel) this.mAdapter.getItem(2)).getData());
        newEntry.setmRate(Double.valueOf(Double.parseDouble(((ItemModel) this.mAdapter.getItem(3)).getData())));
        newEntry.setmRemarks(((ItemModel) this.mAdapter.getItem(4)).getData());
        newEntry.setDateTime(((ItemModel) this.mAdapter.getItem(5)).getData());
        newEntry.setSellerName(((ItemModel) this.mAdapter.getItem(7)).getData());
        Toast.makeText(this, "Updated", 0).show();
        this.f77db.collection(this.user.getUid()).document(this.mEntryId).set(newEntry);
        Toast.makeText(this, "Entry Updated", 0).show();
    }

    /* access modifiers changed from: private */
    public void deleteEntry() {
        this.f77db.collection(this.user.getUid()).document(this.mEntryId).delete();
        Toast.makeText(this, "Entry Deleted", 0).show();
    }

    /* access modifiers changed from: private */
    public void setupAdapter() {
        this.mAdapter = new ItemEntryAdapter(this, this.mItems);
        this.mListView.setAdapter(this.mAdapter);
    }

    public String getData(int position) {
        return ((ItemModel) this.mAdapter.getItem(position)).getData();
    }

    private void setFields() {
        this.mEntryId = getIntent().getStringExtra(CommodityEntryAdapter.ENTRY_ID_KEY);
        if (this.mEntryId == null) {
            finish();
        }
        new Bundle().putString(ARGS_ID_KEY, this.mEntryId);
        this.f77db.collection(this.user.getUid()).document(this.mEntryId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                CommodityEntry entry = (CommodityEntry) documentSnapshot.toObject(CommodityEntry.class);
                CommodityEntryActivity.this.mItems.add(new ItemModel("Mandi", entry.getMandiName()));
                CommodityEntryActivity.this.mItems.add(new ItemModel("Commodity", entry.getmCommodity()));
                CommodityEntryActivity.this.mItems.add(new ItemModel("Grade", entry.getmGradeType()));
                CommodityEntryActivity.this.mItems.add(new ItemModel("Rate", Double.toString(entry.getmRate().doubleValue())));
                CommodityEntryActivity.this.mItems.add(new ItemModel("Remarks", entry.getmRemarks()));
                CommodityEntryActivity.this.mItems.add(new ItemModel(HttpHeaders.DATE, entry.getDateTime()));
                CommodityEntryActivity.this.mItems.add(new ItemModel("Image Url", entry.getImageUrl()));
                CommodityEntryActivity.this.mItems.add(new ItemModel("Seller Name", entry.getSellerName()));
                CommodityEntryActivity.this.setupAdapter();
            }
        });
    }
}
