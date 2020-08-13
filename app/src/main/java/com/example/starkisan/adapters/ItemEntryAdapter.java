package com.example.starkisan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.starkisan.R;
import com.example.starkisan.fragments.CustomDialogFragment;
import com.example.starkisan.models.ItemModel;
import java.util.ArrayList;

public class ItemEntryAdapter extends ArrayAdapter<ItemModel> {
    private static final String TAG = "item_entry_adapter";
    private Context context;
    private ArrayList<ItemModel> items;

    public ItemEntryAdapter(Context context, ArrayList<ItemModel> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ItemModel item = items.get(position);
        view = LayoutInflater.from(context).inflate(R.layout.item_entry, parent, false);
        TextView data = (TextView) view.findViewById(R.id.text_data);
        ((TextView) view.findViewById(R.id.text_title)).setText(item.getTitle());
        data.setText(item.getData());
        view.setOnClickListener(onEntryClick);
        return view;
    }

    /* access modifiers changed from: private */
    public void displayDialog(int id) {
        CustomDialogFragment.newInstance(id).show(((AppCompatActivity) context).getSupportFragmentManager(), TAG);
    }

    private OnClickListener onEntryClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (((TextView) v.findViewById(R.id.text_title)).getText().toString()) {
                // display the appropriate dialog using ID
                case "Date":
                    displayDialog(CustomDialogFragment.DATE_DIALOG_ID);
                    break;
                case "Mandi":
                    displayDialog(CustomDialogFragment.MANDI_DIALOG_ID);
                    break;
                case "Commodity":
                    displayDialog(CustomDialogFragment.COMMODITY_DIALOG_ID);
                    break;
                case "Rate":
                    displayDialog(CustomDialogFragment.RATE_DIALOG_ID);
                    break;
                case "Remarks":
                    displayDialog(CustomDialogFragment.REMARKS_DIALOG_ID);
                    break;
                case "Image Url":
                    displayDialog(CustomDialogFragment.PHOTO_PICKER_DIALOG_ID);
                    break;
                case "Seller Name":
                    displayDialog(CustomDialogFragment.SELLER_DIALOG_ID);
                    break;
                case "Grade":
                    displayDialog(CustomDialogFragment.GRADE_DIALOG_ID);
                    break;
            }
        }
    };
}
