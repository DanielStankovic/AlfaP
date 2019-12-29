package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class PriceList {

    @SerializedName("PriceListID")
    private int priceListID;
    @SerializedName("PriceListCode")
    private String priceListCode;
    @SerializedName("EntityType")
    private int entityType;
    @SerializedName("EntityCode")
    private String entityCode;
    @SerializedName("Price")
    private double price;
    @SerializedName("PriceListDate")
    private String priceListDate;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public PriceList(int priceListID, String priceListCode, int entityType, String entityCode, double price, String priceListDate, String modifiedDate, boolean isActive) {
        this.priceListID = priceListID;
        this.priceListCode = priceListCode;
        this.entityType = entityType;
        this.entityCode = entityCode;
        this.price = price;
        this.priceListDate = priceListDate;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public PriceList() {
    }

    public int getPriceListID() {
        return priceListID;
    }

    public void setPriceListID(int priceListID) {
        this.priceListID = priceListID;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getPriceListCode() {
        return priceListCode;
    }

    public void setPriceListCode(String priceListCode) {
        this.priceListCode = priceListCode;
    }

    public String getPriceListDate() {
        return priceListDate;
    }

    public void setPriceListDate(String priceListDate) {
        this.priceListDate = priceListDate;
    }
}
