package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class WarehouseState {

    @SerializedName("MaterialID")
    private int materialID;
    @SerializedName("WarehouseID")
    private int warehouseID;
    @SerializedName("RealQuantity")
    private double realQuantity;
    @SerializedName("ReservedQuantity")
    private double reservedQuantity;
    @SerializedName("ModifiedDate")
    private String modifiedDate;

    public WarehouseState(int materialID, int warehouseID, double realQuantity, double reservedQuantity, String modifiedDate) {
        this.materialID = materialID;
        this.warehouseID = warehouseID;
        this.realQuantity = realQuantity;
        this.reservedQuantity = reservedQuantity;
        this.modifiedDate = modifiedDate;
    }

    public WarehouseState() {
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
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

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
