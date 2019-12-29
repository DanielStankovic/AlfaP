package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class Material {

    @SerializedName("MaterialID")
    private int materialID;
    @SerializedName("MaterialName")
    private String materialName;
    @SerializedName("MaterialCode")
    private String materialCode;
    @SerializedName("UnitOfMeasure")
    private String unitOfMeasure;
    @SerializedName("QuantitySpent")
    private int quantitySpent;
    @SerializedName("CreatedDate")
    private String createdDate;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    private double price;

    //ovo je dodato zbog izlistavanja na stanju magacina. Nema serialized name zato sto se ne vuce iz tabele Material na serveru.
    private double realQuantity;
    private double reservedQuantity;



    public Material(int materialID, String materialName, String materialCode,
                    String unitOfMeasure, String modifiedDate, boolean isActive) {
        this.materialID = materialID;
        this.materialName = materialName;
        this.materialCode = materialCode;
        this.unitOfMeasure = unitOfMeasure;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public Material() {
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
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
        isActive = active;
    }

    public int getQuantitySpent() {
        return quantitySpent;
    }

    public void setQuantitySpent(int quantitySpent) {
        this.quantitySpent = quantitySpent;
    }

    public double getRealQuantity() {
        return realQuantity;
    }

    public void setRealQuantity(double realQuantity) {
        this.realQuantity = realQuantity;
    }

    public double getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(double reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
