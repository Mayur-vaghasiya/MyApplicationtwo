package com.example.myapplicationtwo.model;

public class UnitList {

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getComplrtionStatus() {
        return complrtionStatus;
    }

    public void setComplrtionStatus(int complrtionStatus) {
        this.complrtionStatus = complrtionStatus;
    }

    public String getStapeName() {
        return stapeName;
    }

    public void setStapeName(String stapeName) {
        this.stapeName = stapeName;
    }

    String unitname;
    String activityname;
    int totalDays;
    int complrtionStatus;
    String stapeName;


    public UnitList(String unitname, String activityname, int totalDays, int complrtionStatus, String stapeName) {
        this.unitname = unitname;
        this.activityname = activityname;
        this.totalDays = totalDays;
        this.complrtionStatus = complrtionStatus;
        this.stapeName = stapeName;
    }

}
