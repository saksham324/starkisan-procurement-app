package com.example.starkisan.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.starkisan.R;
import com.example.starkisan.CommodityEntryActivity;
import com.example.starkisan.models.CommodityEntry;
import java.util.ArrayList;

public class CommodityEntryAdapter extends ArrayAdapter<CommodityEntry> {
    private static final String ARGS_ID_KEY = "args_id_key";
    public static final String ENTRY_ID_KEY = "entry_id";
    private static final String TAG = "commodity_entry_adapter";
    private Context context;
    private ArrayList<CommodityEntry> items;

    public CommodityEntryAdapter(Context context, ArrayList<CommodityEntry> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CommodityEntry item = items.get(position);
        view = LayoutInflater.from(context).inflate(R.layout.item_activity_entry, parent, false);
        view.setTag(position);
        TextView datetimeView = (TextView) view.findViewById(R.id.entry_datetime);
        TextView rateView = (TextView) view.findViewById(R.id.entry_rate);
        TextView sourceView = view.findViewById(R.id.entry_commodity);
        TextView commodityView = view.findViewById(R.id.entry_source);
        sourceView.setText(item.getmCommodity());
        commodityView.setText(item.getMandiName());
        datetimeView.setText(item.getDateTime());
        rateView.setText(Double.toString(item.getmRate().doubleValue()));
        view.setOnClickListener(onEntryClick);
        return view;
    }

    private OnClickListener onEntryClick = new OnClickListener() {
        public void onClick(View v) {
            String id = items.get((Integer) v.getTag()).getId();
            Intent intent = new Intent(getContext(), CommodityEntryActivity.class);
            intent.putExtra(CommodityEntryAdapter.ENTRY_ID_KEY, id);
            getContext().startActivity(intent);
        }
    };
}
