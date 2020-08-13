package com.example.starkisan.models;

import java.util.List;

public class PreferenceModel {
    private List<String> commodityList;
    private List<String> gradeList;
    private List<String> sourceList;

    public PreferenceModel(List<String> sourceList, List<String> commodityList, List<String> gradeList) {
        this.sourceList = sourceList;
        this.commodityList = commodityList;
        this.gradeList = gradeList;
    }

    public PreferenceModel() {
    }

    public List<String> getSourceList() {
        return this.sourceList;
    }

    public void setSourceList(List<String> sourceList) {
        this.sourceList = sourceList;
    }

    public List<String> getCommodityList() {
        return this.commodityList;
    }

    public void setCommodityList(List<String> commodityList) {
        this.commodityList = commodityList;
    }

    public List<String> getGradeList() {
        return this.gradeList;
    }

    public void setGradeList(List<String> gradeList) {
        this.gradeList = gradeList;
    }
}
