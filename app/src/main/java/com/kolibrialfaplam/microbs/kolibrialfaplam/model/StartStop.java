package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class StartStop {

    @SerializedName("EmployeeID")
    private int employeeID;
    @SerializedName("Time")
    private String time;
    @SerializedName("IsStarted")
    private boolean isStarted;

    public StartStop(int employeeID, String time, boolean isStarted) {
        this.employeeID = employeeID;
        this.time = time;
        this.isStarted = isStarted;
    }

    public StartStop() {
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
