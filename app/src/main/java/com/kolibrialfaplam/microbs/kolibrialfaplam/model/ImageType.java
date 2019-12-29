package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class ImageType {

    @SerializedName("ImageTypeID")
    private int imageTypeID;
    @SerializedName("ImageTypeCode")
    private int imageTypeCode;
    @SerializedName("ImageTypeName")
    private String imageTypeName;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public ImageType(int imageTypeID, int imageTypeCode, String imageTypeName, String modifiedDate, boolean isActive) {
        this.imageTypeID = imageTypeID;
        this.imageTypeCode = imageTypeCode;
        this.imageTypeName = imageTypeName;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public ImageType() {
    }

    public int getImageTypeID() {
        return imageTypeID;
    }

    public void setImageTypeID(int imageTypeID) {
        this.imageTypeID = imageTypeID;
    }

    public int getImageTypeCode() {
        return imageTypeCode;
    }

    public void setImageTypeCode(int imageTypeCode) {
        this.imageTypeCode = imageTypeCode;
    }

    public String getImageTypeName() {
        return imageTypeName;
    }

    public void setImageTypeName(String imageTypeName) {
        this.imageTypeName = imageTypeName;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
