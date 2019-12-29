package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class Failure {

    @SerializedName("FailureID")
    private int failureID;
    @SerializedName("FailureName")
    private String failureName;
    @SerializedName("FailureCode")
    private String failureCode;
    @SerializedName("Description")
    private String description;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public Failure(int failureID, String failureName, String failureCode, String description, String modifiedDate, boolean isActive) {
        this.failureID = failureID;
        this.failureName = failureName;
        this.failureCode = failureCode;
        this.description = description;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public Failure() {
    }

    public int getFailureID() {
        return failureID;
    }

    public void setFailureID(int failureID) {
        this.failureID = failureID;
    }

    public String getFailureName() {
        return failureName;
    }

    public void setFailureName(String failureName) {
        this.failureName = failureName;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
