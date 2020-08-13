package com.example.starkisan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.starkisan.models.CommodityEntry;
import java.io.File;
import java.util.ArrayList;

public class CommodityEntryHelper extends SQLiteOpenHelper {
    public static final String COLUMN_COMMODITY = "commodity_name";
    public static final String COLUMN_DATE_TIME = "date_time";
    private static final String COLUMN_GRADE_TYPE = "grade_type";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_MANDI = "mandi";
    private static final String COLUMN_RATE = "rate";
    private static final String COLUMN_REMARKS = "remarks";
    private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS COMMODITIES (_id INTEGER PRIMARY KEY AUTOINCREMENT, mandi TEXT, commodity_name TEXT, grade_type TEXT, rate FLOAT NOT NULL, remarks TEXT, date_time TEXT NOT NULL, image TEXT);";
    private static final String DATABASE_NAME = "commodities.db";
    private static final int DATABASE_VERSION = 8;
    public static final String TABLE_ENTRIES = "COMMODITIES";
    private static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_MANDI, COLUMN_COMMODITY, COLUMN_GRADE_TYPE, COLUMN_RATE, COLUMN_REMARKS, COLUMN_DATE_TIME, COLUMN_IMAGE};

    public CommodityEntryHelper(Context context) {
        super(context, DATABASE_NAME, null, 8);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String name = CommodityEntryHelper.class.getName();
        StringBuilder sb = new StringBuilder();
        sb.append("Upgrading database from version ");
        sb.append(oldVersion);
        sb.append(" to ");
        sb.append(newVersion);
        sb.append(", which will destroy all old data");
        Log.w(name, sb.toString());
        db.execSQL("DROP TABLE IF EXISTS COMMODITIES");
        onCreate(db);
    }

    public void insertEntry(CommodityEntry entry) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MANDI, entry.getMandiName());
        values.put(COLUMN_COMMODITY, entry.getmCommodity());
        values.put(COLUMN_GRADE_TYPE, entry.getmGradeType());
        values.put(COLUMN_RATE, entry.getmRate());
        values.put(COLUMN_REMARKS, entry.getmRemarks());
        values.put(COLUMN_DATE_TIME, entry.getDateTime());
        values.put(COLUMN_IMAGE, entry.getmImageBytes());
        getWritableDatabase().insert(TABLE_ENTRIES, null, values);
        close();
    }

    public void removeEntry(long rowIndex) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("_id = ");
        sb.append(rowIndex);
        writableDatabase.delete(TABLE_ENTRIES, sb.toString(), null);
        close();
    }

    public CommodityEntry fetchEntryByIndex(long rowId) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        String[] strArr = ALL_COLUMNS;
        StringBuilder sb = new StringBuilder();
        sb.append("_id = ");
        sb.append(rowId);
        Cursor cursor = readableDatabase.query(TABLE_ENTRIES, strArr, sb.toString(), null, null, null, null);
        cursor.moveToFirst();
        CommodityEntry entry = entryFromCursor(cursor);
        cursor.close();
        close();
        return entry;
    }

    private CommodityEntry entryFromCursor(Cursor cursor) {
        CommodityEntry entry = new CommodityEntry();
        entry.setId(cursor.getString(0));
        entry.setMandiName(cursor.getString(1));
        entry.setmCommodity(cursor.getString(2));
        entry.setmGradeType(cursor.getString(3));
        entry.setmRate(Double.valueOf(cursor.getDouble(4)));
        entry.setmRemarks(cursor.getString(5));
        entry.setDateTime(cursor.getString(6));
        entry.setmImage(cursor.getString(7));
        return entry;
    }

    public ArrayList<CommodityEntry> fetchEntries() {
        ArrayList<CommodityEntry> entries = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(TABLE_ENTRIES, ALL_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            entries.add(entryFromCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return entries;
    }

    public long updateEntry(long rowId) {
        CommodityEntry entry = fetchEntryByIndex(rowId);
        ContentValues new_values = new ContentValues();
        new_values.put(COLUMN_MANDI, entry.getMandiName());
        new_values.put(COLUMN_COMMODITY, entry.getmCommodity());
        new_values.put(COLUMN_GRADE_TYPE, entry.getmGradeType());
        new_values.put(COLUMN_RATE, entry.getmRate());
        new_values.put(COLUMN_REMARKS, entry.getmRemarks());
        new_values.put(COLUMN_DATE_TIME, entry.getDateTime());
        new_values.put(COLUMN_IMAGE, entry.getmImageBytes());
        SQLiteDatabase readableDatabase = getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("_id=");
        sb.append(rowId);
        readableDatabase.update(TABLE_ENTRIES, new_values, sb.toString(), null);
        return rowId;
    }

    public File getDatabasePath(Context context) {
        return context.getDatabasePath(DATABASE_NAME);
    }

    public ArrayList<CommodityEntry> fetchEntriesByCriteria(String criteria) {
        ArrayList<CommodityEntry> entries = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM COMMODITIES WHERE date_time LIKE ");
        sb.append(criteria);
        Cursor cursor = readableDatabase.rawQuery(sb.toString(), null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            entries.add(entryFromCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return entries;
    }
}
