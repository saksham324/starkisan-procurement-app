package com.example.starkisan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.starkisan.R;
import com.example.starkisan.models.RowModel;
import java.util.ArrayList;

public class RowAdapter extends ArrayAdapter {
    Context context;
    ArrayList<RowModel> rows;

    public RowAdapter(Context context, ArrayList<RowModel> rows) {
        super(context, 0, rows);
        this.context = context;
        this.rows = rows;
    }

    public View getView(int position, View view, ViewGroup parent) {
        RowModel row = (RowModel) rows.get(position);
        view = LayoutInflater.from(context).inflate(R.layout.row_seller_activity, parent, false);
        view.setTag(Integer.valueOf(position));
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        ((TextView) view.findViewById(R.id.txtName)).setText(row.getText());
        checkBox.setChecked(row.getChecked());
        return view;
    }
}
