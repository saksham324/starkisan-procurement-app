package com.example.starkisan.models;

import java.util.List;

public class SellerEntry {
    private String comments;
    private List<String> commodities;

    /* renamed from: id */
    private String id;
    private String mandi;
    private String name;
    private String phone;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SellerEntry() {
    }

    public SellerEntry(String name, String phone, String comments, List<String> commodities, String mandi) {
        this.name = name;
        this.phone = phone;
        this.comments = comments;
        this.commodities = commodities;
        this.mandi = mandi;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getCommodities() {
        return this.commodities;
    }

    public void setCommodities(List<String> commodities) {
        this.commodities = commodities;
    }

    public String getMandi() {
        return this.mandi;
    }

    public void setMandi(String mandi) {
        this.mandi = mandi;
    }
}
