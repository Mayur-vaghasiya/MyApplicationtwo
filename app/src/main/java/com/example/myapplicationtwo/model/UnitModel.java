package com.example.myapplicationtwo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class UnitModel implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("units")
    @Expose
    private ArrayList<Unit> units = null;
    private final static long serialVersionUID = -3755284257693224312L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }
    public class Unit implements Serializable
    {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("activities")
        @Expose
        private ArrayList<Activity> activities = null;
        private final static long serialVersionUID = 2147271696175089390L;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Activity> getActivities() {
            return activities;
        }

        public void setActivities(ArrayList<Activity> activities) {
            this.activities = activities;
        }
        public class Activity implements Serializable
        {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("stepName")
            @Expose
            private String stepName;
            @SerializedName("totalDays")
            @Expose
            private int totalDays;
            @SerializedName("completionStatus")
            @Expose
            private int completionStatus;
            private final static long serialVersionUID = -2038823129522870438L;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStepName() {
                return stepName;
            }

            public void setStepName(String stepName) {
                this.stepName = stepName;
            }

            public int getTotalDays() {
                return totalDays;
            }

            public void setTotalDays(int totalDays) {
                this.totalDays = totalDays;
            }

            public int getCompletionStatus() {
                return completionStatus;
            }

            public void setCompletionStatus(int completionStatus) {
                this.completionStatus = completionStatus;
            }

        }
    }
}
