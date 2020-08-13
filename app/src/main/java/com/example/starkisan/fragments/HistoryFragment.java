package com.example.starkisan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.starkisan.R;
import com.example.starkisan.adapters.CommodityEntryAdapter;
import com.example.starkisan.models.CommodityEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Iterator;

public class HistoryFragment extends Fragment {
    private static final int LOADER_ID = 0;
    private static final String PAGE_NUMBER = "page_number";
    public static final String TAG = "history_fragment";

    /* renamed from: db */
    private FirebaseFirestore f101db;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    /* access modifiers changed from: private */
    public CommodityEntryAdapter mAdapter;
    private int position;
    FloatingActionButton viewPhotoBtn;

    public static HistoryFragment newInstance(int position2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE_NUMBER, position2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.position = getArguments().getInt(PAGE_NUMBER);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView lv = (ListView) view.findViewById(R.id.historyListView);
        this.mAdapter = new CommodityEntryAdapter(getContext(), new ArrayList());
        lv.setAdapter(this.mAdapter);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.f101db = FirebaseFirestore.getInstance();
    }

    public void onResume() {
        super.onResume();
        fetchEntries();
    }

    private void fetchEntries() {
        if (this.firebaseUser == null) {
            Toast.makeText(getContext(), "No user found", Toast.LENGTH_SHORT).show();
            return;
        }
        this.mAdapter.clear();
        String uid = this.firebaseUser.getUid();
        this.f101db = FirebaseFirestore.getInstance();
        this.f101db.collection(uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Iterator it = ((QuerySnapshot) task.getResult()).iterator();
                    while (it.hasNext()) {
                        HistoryFragment.this.mAdapter.add((CommodityEntry) ((QueryDocumentSnapshot) it.next()).toObject(CommodityEntry.class));
                    }
                    HistoryFragment.this.mAdapter.notifyDataSetChanged();
                    return;
                }
                Toast.makeText(HistoryFragment.this.getContext(), "Error getting data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
