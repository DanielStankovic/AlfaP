package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class WorkOrderImage {

    @SerializedName("WorkOrderID")
    private int workOrderID;
    @SerializedName("ImageTypeID")
    private int imageTypeID;
    @SerializedName("ImageTitle")
    private String imageTitle;
    @SerializedName("ImageString")
    private String imageString;
    @SerializedName("Note")
    private String note;
    @SerializedName("CreatedDate")
    private String createdDate;

    public WorkOrderImage(int workOrderID, int imageTypeID, String imageTitle, String imageString, String note, String createdDate) {
        this.workOrderID = workOrderID;
        this.imageTypeID = imageTypeID;
        this.imageTitle = imageTitle;
        this.imageString = imageString;
        this.note = note;
        this.createdDate = createdDate;
    }

    public WorkOrderImage() {
    }

    public int getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(int workOrderID) {
        this.workOrderID = workOrderID;
    }

    public int getImageTypeID() {
        return imageTypeID;
    }

    public void setImageTypeID(int imageTypeID) {
        this.imageTypeID = imageTypeID;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
