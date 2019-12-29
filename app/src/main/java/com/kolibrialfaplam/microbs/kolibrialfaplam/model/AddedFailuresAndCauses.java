package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class AddedFailuresAndCauses {

    @SerializedName("FailureID")
    private int failureID;
    @SerializedName("FailureCauseID")
    private int failureCauseID;
    @SerializedName("FailureName")
    private String failureName;
    @SerializedName("FailureCauseName")
    private String failureCauseName;
    @SerializedName("CreatedDate")
    private String createdDate;

    public AddedFailuresAndCauses(int failureID, int failureCauseID, String failureName,
                                  String failureCauseName, String createdDate) {
        this.failureID = failureID;
        this.failureCauseID = failureCauseID;
        this.failureName = failureName;
        this.failureCauseName = failureCauseName;
        this.createdDate = createdDate;
    }

    public AddedFailuresAndCauses() {
    }

    public int getFailureID() {
        return failureID;
    }

    public int getFailureCauseID() {
        return failureCauseID;
    }

    public String getFailureName() {
        return failureName;
    }

    public String getFailureCauseName() {
        return failureCauseName;
    }

    public void setFailureID(int failureID) {
        this.failureID = failureID;
    }

    public void setFailureCauseID(int failureCauseID) {
        this.failureCauseID = failureCauseID;
    }

    public void setFailureName(String failureName) {
        this.failureName = failureName;
    }

    public void setFailureCauseName(String failureCauseName) {
        this.failureCauseName = failureCauseName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
