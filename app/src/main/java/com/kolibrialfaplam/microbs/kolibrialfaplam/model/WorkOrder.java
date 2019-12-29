package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkOrder {

    @SerializedName("WorkOrderID")
    private int workOrderID;
    @SerializedName("WorkOrderName")
    private String workOrderName;
    @SerializedName("WorkOrderCode")
    private String workOrderCode;
    @SerializedName("UpisCode")
    private String upisCode;
    @SerializedName("WorkOrderTypeID")
    private int workOrderTypeID;
    @SerializedName("WorkOrderPollID")
    private int workOrderPollID;
    @SerializedName("WorkOrderDate")
    private String workOrderDate;
    @SerializedName("PartnerID")
    private int partnerID;
    @SerializedName("CustomerName")
    private String customerName;
    @SerializedName("CustomerAddress")
    private String customerAddress;
    @SerializedName("CustomerAddressNo")
    private String customerAddressNo;
    @SerializedName("CustomerCity")
    private String customerCity;
    @SerializedName("CustomerPhone")
    private String customerPhone;
    @SerializedName("IsLegalPerson")
    private boolean isLegalPerson;
    @SerializedName("CustomerWarning")
    private boolean customerWarning;
    @SerializedName("StatusID")
    private int statusID;
    @SerializedName("ProductID")
    private int productID;
    @SerializedName("Quantity")
    private double quantity;
    @SerializedName("RegionID")
    private int regionID;
    @SerializedName("RegionPlaceID")
    private int regionPlaceID;
    @SerializedName("PlannedDate")
    private String plannedDate;
    @SerializedName("RealizationDate")
    private String realizationDate;
    @SerializedName("InWarranty")
    private boolean inWarranty;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("Description")
    private String description;
    @SerializedName("Note")
    private String note;
    @SerializedName("PriorityTypeID")
    private int priorityTypeID;
    @SerializedName("MaterialTypeID")
    private int materialTypeID;
    @SerializedName("ProductColorID")
    private int productColorID;
    @SerializedName("WorkOrderServiceList")
    private List<Integer> workOrderServiceList;
    @SerializedName("WorkOrderFailureCauseList")
    private List<FailureCause> workOrderFailureCauseList;


    public WorkOrder(int workOrderID, String workOrderName, String workOrderCode, String upisCode,
                     int workOrderTypeID, int workOrderPollID, String workOrderDate, int partnerID,
                     String customerName, String customerAddress, String customerAddressNo,
                     String customerCity, String customerPhone, boolean isLegalPerson,
                     boolean customerWarning, int statusID, int productID, double quantity,
                     int regionID, int regionPlaceID, String plannedDate, String realizationDate,
                     boolean inWarranty, String modifiedDate, String description, String note,
                     int priorityTypeID, int materialTypeID, int productColorID,
                     List<Integer> workOrderServiceList, List<FailureCause> workOrderFailureCauseList) {
        this.workOrderID = workOrderID;
        this.workOrderName = workOrderName;
        this.workOrderCode = workOrderCode;
        this.upisCode = upisCode;
        this.workOrderTypeID = workOrderTypeID;
        this.workOrderPollID = workOrderPollID;
        this.workOrderDate = workOrderDate;
        this.partnerID = partnerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerAddressNo = customerAddressNo;
        this.customerCity = customerCity;
        this.customerPhone = customerPhone;
        this.isLegalPerson = isLegalPerson;
        this.customerWarning = customerWarning;
        this.statusID = statusID;
        this.productID = productID;
        this.quantity = quantity;
        this.regionID = regionID;
        this.regionPlaceID = regionPlaceID;
        this.plannedDate = plannedDate;
        this.realizationDate = realizationDate;
        this.inWarranty = inWarranty;
        this.modifiedDate = modifiedDate;
        this.description = description;
        this.note = note;
        this.priorityTypeID = priorityTypeID;
        this.materialTypeID = materialTypeID;
        this.productColorID = productColorID;
        this.workOrderServiceList = workOrderServiceList;
        this.workOrderFailureCauseList = workOrderFailureCauseList;
    }

    public int getMaterialTypeID() {
        return materialTypeID;
    }

    public void setMaterialTypeID(int materialTypeID) {
        this.materialTypeID = materialTypeID;
    }

    public int getProductColorID() {
        return productColorID;
    }

    public void setProductColorID(int productColorID) {
        this.productColorID = productColorID;
    }

    public void setWorkOrderFailureCauseList(List<FailureCause> workOrderFailureCauseList) {
        this.workOrderFailureCauseList = workOrderFailureCauseList;
    }

    public int getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(int workOrderID) {
        this.workOrderID = workOrderID;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public String getWorkOrderCode() {
        return workOrderCode;
    }

    public void setWorkOrderCode(String workOrderCode) {
        this.workOrderCode = workOrderCode;
    }

    public String getUpisCode() {
        return upisCode;
    }

    public void setUpisCode(String upisCode) {
        this.upisCode = upisCode;
    }

    public int getWorkOrderTypeID() {
        return workOrderTypeID;
    }

    public void setWorkOrderTypeID(int workOrderTypeID) {
        this.workOrderTypeID = workOrderTypeID;
    }

    public int getWorkOrderPollID() {
        return workOrderPollID;
    }

    public void setWorkOrderPollID(int workOrderPollID) {
        this.workOrderPollID = workOrderPollID;
    }

    public String getWorkOrderDate() {
        return workOrderDate;
    }

    public void setWorkOrderDate(String workOrderDate) {
        this.workOrderDate = workOrderDate;
    }

    public int getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(int partnerID) {
        this.partnerID = partnerID;
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

    public String getCustomerAddressNo() {
        return customerAddressNo;
    }

    public void setCustomerAddressNo(String customerAddressNo) {
        this.customerAddressNo = customerAddressNo;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public boolean isLegalPerson() {
        return isLegalPerson;
    }

    public void setLegalPerson(boolean legalPerson) {
        isLegalPerson = legalPerson;
    }

    public boolean isCustomerWarning() {
        return customerWarning;
    }

    public void setCustomerWarning(boolean customerWarning) {
        this.customerWarning = customerWarning;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }

    public int getRegionPlaceID() {
        return regionPlaceID;
    }

    public void setRegionPlaceID(int regionPlaceID) {
        this.regionPlaceID = regionPlaceID;
    }

    public String getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(String plannedDate) {
        this.plannedDate = plannedDate;
    }

    public String getRealizationDate() {
        return realizationDate;
    }

    public void setRealizationDate(String realizationDate) {
        this.realizationDate = realizationDate;
    }

    public boolean isInWarranty() {
        return inWarranty;
    }

    public void setInWarranty(boolean inWarranty) {
        this.inWarranty = inWarranty;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPriorityTypeID() {
        return priorityTypeID;
    }

    public void setPriorityTypeID(int priorityTypeID) {
        this.priorityTypeID = priorityTypeID;
    }

    public List<Integer> getWorkOrderServiceList() {
        return workOrderServiceList;
    }

    public void setWorkOrderServiceList(List<Integer> workOrderServiceList) {
        this.workOrderServiceList = workOrderServiceList;
    }

    public List<FailureCause> getWorkOrderFailureCauseList() {
        return workOrderFailureCauseList;
    }

    public void setWorkOrderFailureList(List<FailureCause> workOrderFailureCauseList) {
        this.workOrderFailureCauseList = workOrderFailureCauseList;
    }
}
