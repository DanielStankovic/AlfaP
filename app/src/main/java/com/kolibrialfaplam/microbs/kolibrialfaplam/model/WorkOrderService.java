package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class WorkOrderService {

    @SerializedName("WorkOrderID")
    private int workOrderID;
    @SerializedName("ServiceID")
    private int serviceID;
    @SerializedName("ModifiedDate")
    private String modifiedDate;

    public WorkOrderService(int workOrderID, int serviceID, String modifiedDate) {
        this.workOrderID = workOrderID;
        this.serviceID = serviceID;
        this.modifiedDate = modifiedDate;
    }

    public int getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(int workOrderID) {
        this.workOrderID = workOrderID;
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

