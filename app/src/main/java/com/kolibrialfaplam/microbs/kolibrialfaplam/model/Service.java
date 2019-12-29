package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class Service {

    @SerializedName("ServiceID")
    private int serviceID;
    @SerializedName("ServiceName")
    private String serviceName;
    @SerializedName("ServiceCode")
    private String serviceCode;
    @SerializedName("UnitOfMeasure")
    private String unitOfMeasure;
    @SerializedName("UnitSpent")
    private int unitSpent;
    @SerializedName("CreatedDate")
    private String createdDate;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    private String normTime;
    private double price;


    public Service(int serviceID, String serviceName, String serviceCode, String unitOfMeasure, String modifiedDate, boolean isActive) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceCode = serviceCode;
        this.unitOfMeasure = unitOfMeasure;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public Service() {
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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
        this.isActive = active;
    }

    public int getUnitSpent() {
        return unitSpent;
    }

    public void setUnitSpent(int unitSpent) {
        this.unitSpent = unitSpent;
    }

    public String getNormTime() {
        return normTime;
    }

    public void setNormTime(String normTime) {
        this.normTime = normTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
