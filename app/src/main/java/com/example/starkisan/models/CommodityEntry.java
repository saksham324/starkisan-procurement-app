package com.example.starkisan.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CommodityEntry {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    /* renamed from: id */
    private String f103id;
    private String imageUrl;
    private String mCommodity;
    private Calendar mDateTime;
    private String mGradeType;
    private String mImageBytes;
    private Double mRate;
    private String mRemarks;
    private String mUserName;
    private String mandiName;
    private String sellerName;

    public String getSellerName() {
        return this.sellerName;
    }

    public void setSellerName(String sellerName2) {
        this.sellerName = sellerName2;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl2) {
        this.imageUrl = imageUrl2;
    }

    public String getmUserName() {
        return this.mUserName;
    }

    public void setmUserName(String mUserName2) {
        this.mUserName = mUserName2;
    }

    public String getmImageBytes() {
        return this.mImageBytes;
    }

    public void setmImage(String mImageBytes2) {
        this.mImageBytes = mImageBytes2;
    }

    public String getId() {
        return this.f103id;
    }

    public void setId(String id) {
        this.f103id = id;
    }

    public String getMandiName() {
        return this.mandiName;
    }

    public void setMandiName(String mandiName2) {
        this.mandiName = mandiName2;
    }

    public String getmGradeType() {
        return this.mGradeType;
    }

    public void setmGradeType(String mGradeType2) {
        this.mGradeType = mGradeType2;
    }

    public String getmRemarks() {
        return this.mRemarks;
    }

    public void setmRemarks(String mRemarks2) {
        this.mRemarks = mRemarks2;
    }

    public Double getmRate() {
        return this.mRate;
    }

    public void setmRate(Double mRate2) {
        this.mRate = mRate2;
    }

    public String getmCommodity() {
        return this.mCommodity;
    }

    public void setmCommodity(String mCommodity2) {
        this.mCommodity = mCommodity2;
    }

    public String getDateTime() {
        Calendar calendar = this.mDateTime;
        if (calendar == null) {
            return "";
        }
        return SDF.format(calendar.getTime());
    }

    public void setDateTime(String str) {
        this.mDateTime = Calendar.getInstance();
        try {
            this.mDateTime.setTime(SDF.parse(str));
        } catch (ParseException e) {
        }
    }

    public String[] String() {
        return new String[]{this.f103id, this.mandiName, this.mCommodity, this.mGradeType, Double.toString(this.mRate.doubleValue()), this.mRemarks, this.mDateTime.getTime().toString(), this.mUserName, this.imageUrl, this.sellerName};
    }
}
