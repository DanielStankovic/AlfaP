package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("WorkOrderID")
    private int workOrderID;
    @SerializedName("WorkOrderResultID")
    private int workOrderResultID;
    @SerializedName("WorkOrderCode")
    private String workOrderCode;
    @SerializedName("CustomerName")
    private String customerName;
    @SerializedName("CustomerAddress")
    private String customerAddress;
    @SerializedName("CustomerCity")
    private String customerCity;
    @SerializedName("ProductName")
    private String productName;
    @SerializedName("PartnerName")
    private String partnerName;
    @SerializedName("CreatedDate")
    private String createdDate;
    @SerializedName("SentDate")
    private String sentDate;

    public int getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(int workOrderID) {
        this.workOrderID = workOrderID;
    }

    public int getWorkOrderResultID() {
        return workOrderResultID;
    }

    public void setWorkOrderResultID(int workOrderResultID) {
        this.workOrderResultID = workOrderResultID;
    }

    public String getWorkOrderCode() {
        return workOrderCode;
    }

    public void setWorkOrderCode(String workOrderCode) {
        this.workOrderCode = workOrderCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }
}
