package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("ProductID")
    private int productID;
    @SerializedName("ProductName")
    private String productName;
    @SerializedName("ProductCode")
    private String productCode;
    @SerializedName("Model")
    private String model;
    @SerializedName("Barcode")
    private String barcode;
    @SerializedName("Description")
    private String description;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("UnitOfMeasure")
    private String unitOfMeasure;

    @SerializedName("ProductRangeID")
    private int productRangeID;




    @SerializedName("Width")
    private double width;
    @SerializedName("Height")
    private double height;
    @SerializedName("Length")
    private double length;
    @SerializedName("Weight")
    private double weight;

    @SerializedName("IsActive")
    private boolean isActive;

    public Product(int productID, String productName, String productCode, String model, String barcode,
                   String description, String modifiedDate, String unitOfMeasure, int productRangeID,
                   double width, double height, double length, double weight, boolean isActive) {
        this.productID = productID;
        this.productName = productName;
        this.productCode = productCode;
        this.model = model;
        this.barcode = barcode;
        this.description = description;
        this.modifiedDate = modifiedDate;
        this.unitOfMeasure = unitOfMeasure;
        this.productRangeID = productRangeID;
        this.width = width;
        this.height = height;
        this.length = length;
        this.weight = weight;
        this.isActive = isActive;
    }

    public Product() {
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getProductRangeID() {
        return productRangeID;
    }

    public void setProductRangeID(int productRangeID) {
        this.productRangeID = productRangeID;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
