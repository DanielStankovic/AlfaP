package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class SignatureImage {

    @SerializedName("WorkOrderID")
    private int workOrderID;
    @SerializedName("ImageTitle")
    private String imageTitle;
    @SerializedName("ImageString")
    private String imageString;
    @SerializedName("CreatedDate")
    private String createdDate;

    public SignatureImage(int workOrderID, String imageTitle, String imageString, String createdDate) {
        this.workOrderID = workOrderID;
        this.imageTitle = imageTitle;
        this.imageString = imageString;
        this.createdDate = createdDate;
    }

    public int getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(int workOrderID) {
        this.workOrderID = workOrderID;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
