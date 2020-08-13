package com.example.starkisan.models;

public class RowModel {
    Boolean checked;
    private String text;

    public RowModel(String text, Boolean checked) {
        this.text = text;
        this.checked = checked;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
