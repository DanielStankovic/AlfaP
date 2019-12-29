package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class ProductRange {

    @SerializedName("ProductRangeID")
    private int productRangeID;
    @SerializedName("ProductRangeName")
    private String productRangeName;
    @SerializedName("ProductRangeCode")
    private String productRangeCode;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public ProductRange(int productRangeID, String productRangeName,
                        String productRangeCode, String modifiedDate, boolean isActive) {
        this.productRangeID = productRangeID;
        this.productRangeName = productRangeName;
        this.productRangeCode = productRangeCode;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public ProductRange() {
    }

    public int getProductRangeID() {
        return productRangeID;
    }

    public void setProductRangeID(int productRangeID) {
        this.productRangeID = productRangeID;
    }

    public String getProductRangeName() {
        return productRangeName;
    }

    public void setProductRangeName(String productRangeName) {
        this.productRangeName = productRangeName;
    }

    public String getProductRangeCode() {
        return productRangeCode;
    }

    public void setProductRangeCode(String productRangeCode) {
        this.productRangeCode = productRangeCode;
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
