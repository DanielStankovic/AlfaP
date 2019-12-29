package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class CheckIn {

    @SerializedName("CheckInID")
    private String checkInID;
    @SerializedName("WorkOrderID")
    private int workOrderID;
    @SerializedName("EmployeeID")
    private int employeeID;
    @SerializedName("CheckInDate")
    private String checkInDate;
    @SerializedName("CheckOutDate")
    private String checkOutDate;
    @SerializedName("Comment")
    private String comment;
    @SerializedName("Mileage")
    private String mileage;
    @SerializedName("CheckInCoordinates")
    private String checkInCoordinates;
    @SerializedName("IsInRoute")
    private boolean isInRoute;


    public CheckIn(String checkInID, int workOrderID, int employeeID, String checkInDate,
                   String checkOutDate, String comment, String mileage, String checkInCoordinates,
                   boolean isInRoute) {
        this.checkInID = checkInID;
        this.workOrderID = workOrderID;
        this.employeeID = employeeID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.comment = comment;
        this.mileage = mileage;
        this.checkInCoordinates = checkInCoordinates;
        this.isInRoute = isInRoute;
    }

    public CheckIn() {
    }

    public String getCheckInID() {
        return checkInID;
    }

    public void setCheckInID(String checkInID) {
        this.checkInID = checkInID;
    }

    public int getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(int workOrderID) {
        this.workOrderID = workOrderID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getCheckInCoordinates() {
        return checkInCoordinates;
    }

    public void setCheckInCoordinates(String checkInCoordinates) {
        this.checkInCoordinates = checkInCoordinates;
    }

    public boolean isInRoute() {
        return isInRoute;
    }

    public void setInRoute(boolean inRoute) {
        isInRoute = inRoute;
    }
}
