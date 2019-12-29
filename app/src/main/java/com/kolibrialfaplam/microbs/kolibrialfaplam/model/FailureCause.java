package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class FailureCause {

    @SerializedName("FailureCauseID")
    private int failureCauseID;
    @SerializedName("FailureID")
    private int failureID;
    @SerializedName("FailureCauseName")
    private String failureCauseName;
    @SerializedName("FailureCauseCode")
    private String failureCauseCode;
    @SerializedName("Description")
    private String description;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public FailureCause(int failureCauseID, int failureID, String failureCauseName, String failureCauseCode, String description, String modifiedDate, boolean isActive) {
        this.failureCauseID = failureCauseID;
        this.failureID = failureID;
        this.failureCauseName = failureCauseName;
        this.failureCauseCode = failureCauseCode;
        this.description = description;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public FailureCause() {
    }

    public int getFailureCauseID() {
        return failureCauseID;
    }

    public void setFailureCauseID(int failureCauseID) {
        this.failureCauseID = failureCauseID;
    }

    public int getFailureID() {
        return failureID;
    }

    public void setFailureID(int failureID) {
        this.failureID = failureID;
    }

    public String getFailureCauseName() {
        return failureCauseName;
    }

    public void setFailureCauseName(String failureCauseName) {
        this.failureCauseName = failureCauseName;
    }

    public String getFailureCauseCode() {
        return failureCauseCode;
    }

    public void setFailureCauseCode(String failureCauseCode) {
        this.failureCauseCode = failureCauseCode;
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
