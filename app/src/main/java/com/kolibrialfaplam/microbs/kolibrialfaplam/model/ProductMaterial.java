package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class ProductMaterial {

    @SerializedName("ProductID")
    private int productID;
    @SerializedName("MaterialID")
    private int materialID;
    @SerializedName("ModifiedDate")
    private String modifiedDate;

    public ProductMaterial(int productID, int materialID, String modifiedDate) {
        this.productID = productID;
        this.materialID = materialID;
        this.modifiedDate = modifiedDate;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
