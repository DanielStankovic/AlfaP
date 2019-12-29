package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class ProductFailure {

    @SerializedName("ProductID")
    private int productID;
    @SerializedName("FailureID")
    private int failureID;
    @SerializedName("FailureCauseID")
    private int failureCauseID;
    @SerializedName("ModifiedDate")
    private String modifiedDate;

    public ProductFailure(int productID, int failureID, int failureCauseID, String modifiedDate) {
        this.productID = productID;
        this.failureID = failureID;
        this.failureCauseID = failureCauseID;
        this.modifiedDate = modifiedDate;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getFailureID() {
        return failureID;
    }

    public void setFailureID(int failureID) {
        this.failureID = failureID;
    }

    public int getFailureCauseID() {
        return failureCauseID;
    }

    public void setFailureCauseID(int failureCauseID) {
        this.failureCauseID = failureCauseID;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
