package com.example.starkisan.models;

public class UserModel {
    private Boolean isAdmin;
    private String name;
    private String uid;

    public UserModel() {
    }

    public UserModel(String name, Boolean isAdmin, String uid) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.uid = uid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAdmin() {
        return this.isAdmin;
    }

    public void setAdmin(Boolean admin) {
        this.isAdmin = admin;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
