package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class ServiceNorm {

    @SerializedName("ServiceNormID")
    private int serviceNormID;
    @SerializedName("ServiceID")
    private int serviceID;
    @SerializedName("ProductID")
    private int productID;
    @SerializedName("NormName")
    private String normName;
    @SerializedName("NormCode")
    private String normCode;
    @SerializedName("NormTime")
    private String normTime;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public ServiceNorm(int serviceNormID, int serviceID, int productID, String normName,
                       String normCode, String normTime, String modifiedDate, boolean isActive) {
        this.serviceNormID = serviceNormID;
        this.serviceID = serviceID;
        this.productID = productID;
        this.normName = normName;
        this.normCode = normCode;
        this.normTime = normTime;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public ServiceNorm() {
    }

    public int getServiceNormID() {
        return serviceNormID;
    }

    public void setServiceNormID(int serviceNormID) {
        this.serviceNormID = serviceNormID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getNormName() {
        return normName;
    }

    public void setNormName(String normName) {
        this.normName = normName;
    }

    public String getNormCode() {
        return normCode;
    }

    public void setNormCode(String normCode) {
        this.normCode = normCode;
    }

    public String getNormTime() {
        return normTime;
    }

    public void setNormTime(String normTime) {
        this.normTime = normTime;
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
