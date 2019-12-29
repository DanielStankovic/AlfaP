package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class PriorityType {

    @SerializedName("PriorityTypeID")
    private int priorityTypeID;
    @SerializedName("PriorityTypeName")
    private String priorityTypeName;
    @SerializedName("PriorityTypeCode")
    private String priorityTypeCode;
    @SerializedName("PriorityTypeDescription")
    private String priorityTypeDescription;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public PriorityType(int priorityTypeID, String priorityTypeName, String priorityTypeCode,
                        String priorityTypeDescription, String modifiedDate, boolean isActive) {
        this.priorityTypeID = priorityTypeID;
        this.priorityTypeName = priorityTypeName;
        this.priorityTypeCode = priorityTypeCode;
        this.priorityTypeDescription = priorityTypeDescription;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public int getPriorityTypeID() {
        return priorityTypeID;
    }

    public void setPriorityTypeID(int priorityTypeID) {
        this.priorityTypeID = priorityTypeID;
    }

    public String getPriorityTypeName() {
        return priorityTypeName;
    }

    public void setPriorityTypeName(String priorityTypeName) {
        this.priorityTypeName = priorityTypeName;
    }

    public String getPriorityTypeCode() {
        return priorityTypeCode;
    }

    public void setPriorityTypeCode(String priorityTypeCode) {
        this.priorityTypeCode = priorityTypeCode;
    }

    public String getPriorityTypeDescription() {
        return priorityTypeDescription;
    }

    public void setPriorityTypeDescription(String priorityTypeDescription) {
        this.priorityTypeDescription = priorityTypeDescription;
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
