package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostRequest {

    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("EmployeeID")
    private int employeeID;
    @SerializedName("ModifiedDates")
    private List<String> modifiedDates;



    public PostRequest(String modifiedDate, int employeeID) {
        this.modifiedDate = modifiedDate;
        this.employeeID = employeeID;

    }

    public PostRequest(List<String> modifiedDates, int employeeID) {
        this.modifiedDates = modifiedDates;
        this.employeeID = employeeID;
    }
}
