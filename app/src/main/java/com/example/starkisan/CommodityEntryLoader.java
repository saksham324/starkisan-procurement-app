package com.example.starkisan;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;
import com.example.starkisan.models.CommodityEntry;

public class CommodityEntryLoader extends AsyncTaskLoader<CommodityEntry> {

    /* renamed from: id */
    private long f78id;

    public CommodityEntryLoader(Context context, long id) {
        super(context);
        this.f78id = id;
    }

    public CommodityEntry loadInBackground() {
        return new CommodityEntryHelper(getContext()).fetchEntryByIndex(this.f78id);
    }
}
