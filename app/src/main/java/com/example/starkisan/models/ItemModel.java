package com.example.starkisan.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ItemModel implements Parcelable {
    public static final Creator<ItemModel> CREATOR = new Creator<ItemModel>() {
        public ItemModel createFromParcel(Parcel in) {
            return new ItemModel(in);
        }

        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };
    String data;
    String title;

    public ItemModel(String title2, String data2) {
        this.title = title2;
        this.data = data2;
    }

    private ItemModel(Parcel in) {
        this.title = in.readString();
        this.data = in.readString();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data2) {
        this.data = data2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.data);
    }
}
