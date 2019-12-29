package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class WorkOrderType {

    @SerializedName("WorkOrderTypeID")
    private int workOrderTypeID;
    @SerializedName("TypeName")
    private String typeName;
    @SerializedName("TypeCode")
    private String typeCode;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public WorkOrderType(int workOrderTypeID, String typeName, String typeCode, String modifiedDate, boolean isActive) {
        this.workOrderTypeID = workOrderTypeID;
        this.typeName = typeName;
        this.typeCode = typeCode;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public WorkOrderType() {
    }

    public int getWorkOrderTypeID() {
        return workOrderTypeID;
    }

    public void setWorkOrderTypeID(int workOrderTypeID) {
        this.workOrderTypeID = workOrderTypeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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
