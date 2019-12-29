package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class ProductService {

    @SerializedName("ProductID")
    private int productID;
    @SerializedName("ServiceID")
    private int serviceID;
    @SerializedName("ModifiedDate")
    private String modifiedDate;

    public ProductService(int productID, int serviceID, String modifiedDate) {
        this.productID = productID;
        this.serviceID = serviceID;
        this.modifiedDate = modifiedDate;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
