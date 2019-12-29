package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class Warehouse {

    @SerializedName("WarehouseID")
    private int warehouseID;
    @SerializedName("WarehouseName")
    private String warehouseName;
    @SerializedName("WarehouseCode")
    private String warehouseCode;
    @SerializedName("WarehousePosition")
    private String warehousePosition;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public Warehouse(int warehouseID, String warehouseName, String warehouseCode,
                     String warehousePosition, String modifiedDate, boolean isActive) {
        this.warehouseID = warehouseID;
        this.warehouseName = warehouseName;
        this.warehouseCode = warehouseCode;
        this.warehousePosition = warehousePosition;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public Warehouse() {
    }

    public int getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
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
