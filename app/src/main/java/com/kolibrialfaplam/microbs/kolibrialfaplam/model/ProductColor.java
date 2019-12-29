package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class ProductColor {

    @SerializedName("ProductColorID")
    private int productColorID;
    @SerializedName("ProductColorName")
    private String productColorName;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public ProductColor(int productColorID, String productColorName, String modifiedDate, boolean isActive) {
        this.productColorID = productColorID;
        this.productColorName = productColorName;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public int getProductColorID() {
        return productColorID;
    }

    public void setProductColorID(int productColorID) {
        this.productColorID = productColorID;
    }

    public String getProductColorName() {
        return productColorName;
    }

    public void setProductColorName(String productColorName) {
        this.productColorName = productColorName;
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
