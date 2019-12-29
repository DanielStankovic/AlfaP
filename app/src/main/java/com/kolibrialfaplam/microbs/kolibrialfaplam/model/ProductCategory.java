package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class ProductCategory {

    @SerializedName("ProductCategoryID")
    private int productCategoryID;
    @SerializedName("ProductRangeID")
    private int productRangeID;
    @SerializedName("CategoryName")
    private String categoryName;
    @SerializedName("CategoryCode")
    private String categoryCode;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public ProductCategory(int productCategoryID, int productRangeID,
                           String categoryName, String categoryCode, String modifiedDate, boolean isActive) {
        this.productCategoryID = productCategoryID;
        this.productRangeID = productRangeID;
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public ProductCategory() {
    }

    public int getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(int productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public int getProductRangeID() {
        return productRangeID;
    }

    public void setProductRangeID(int productRangeID) {
        this.productRangeID = productRangeID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
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
