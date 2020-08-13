package com.example.starkisan;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;
import com.example.starkisan.models.CommodityEntry;
import java.util.List;

public class CommodityEntryAllLoader extends AsyncTaskLoader<List<CommodityEntry>> {
    public CommodityEntryAllLoader(Context context) {
        super(context);
    }

    public List<CommodityEntry> loadInBackground() {
        return new CommodityEntryHelper(getContext()).fetchEntries();
    }
}
