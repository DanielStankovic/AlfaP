package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Route {



    private int workOrderResultID;
    @SerializedName("WorkOrderID")
    private int workOrderID;
    @SerializedName("WorkOrderCode")
    private String workOrderCode;
    @SerializedName("WorkOrderType")
    private String workOrderType;
    @SerializedName("InWarranty")
    private boolean inWarranty;
    @SerializedName("PartnerName")
    private String partnerName;
    @SerializedName("PartnerCode")
    private String partnerCode;
    @SerializedName("PartnerAddress")
    private String partnerAddress;
    @SerializedName("PartnerContact")
    private String partnerContact;
    @SerializedName("CustomerName")
    private String customerName;
    @SerializedName("CustomerCity")
    private String customerCity;
    @SerializedName("CustomerAddress")
    private String customerAddress;
    @SerializedName("CustomerAddressNo")
    private String customerAddressNo;
    @SerializedName("CustomerPhone")
    private String customerPhone;
    @SerializedName("CustomerWarning")
    private boolean customerWarning;
    @SerializedName("ProductID")
    private int productID;
    @SerializedName("ProductName")
    private String productName;
    @SerializedName("ProductCode")
    private String productCode;

    private String productModel;
    @SerializedName("SerialNumber")
    private String serialNumber;
    @SerializedName("ProductPurchaseDate")
    private String productPurchaseDate;
    @SerializedName("ProductProductionDate")
    private String productProductionDate;
    @SerializedName("PlannedDate")
    private String plannedDate;

    private int workOrderPollID;

    private int statusID;
    @SerializedName("StatusText")
    private String statusText;
    @SerializedName("WorkOrderNote")
    private String workOrderNote;
    @SerializedName("WorkOrderDescription")
    private String workOrderDescription;
    @SerializedName("FailureCauseNote")
    private String failureCauseNote;
    @SerializedName("IsLegalPerson")
    private boolean isLegalPerson;
    @SerializedName("IsEscalated")
    private boolean isEscalated;
    @SerializedName("IsSerialNoScanned")
    private boolean isSerialNoScanned;
    @SerializedName("HasSignature")
    private boolean hasSignature;
    @SerializedName("IsClosed")
    private boolean isClosed;
    @SerializedName("IsDraft")
    private boolean isDraft;
    @SerializedName("ClosedStatus")
    private int closedStatus;
    @SerializedName("WorkOrderServiceList")
    private List<Service> workOrderServiceList;
    @SerializedName("WorkOrderFailureList")
    private List<AddedFailuresAndCauses> workOrderFailureList;
    @SerializedName("WorkOrderMaterialList")
    private List<Material> workOrderMaterialList;
    @SerializedName("WorkOrderPollList")
    private List<PollQuestion> workOrderPollList;
    @SerializedName("MaterialTypeID")
    private int materialTypeID;
    @SerializedName("ProductColorID")
    private int productColorID;
    @SerializedName("IsCustomerRejected")
    private boolean isCustomerRejected;



    public Route(int workOrderID, String workOrderCode, String workOrderType,
                 String partnerName, String partnerCode, String customerName,
                 String customerCity, String customerAddress, String customerAddressNo, String customerPhone, boolean customerWarning,
                 String productName, String productCode, String productModel, String plannedDate, String statusText) {
        this.workOrderID = workOrderID;
        this.workOrderCode = workOrderCode;
        this.workOrderType = workOrderType;
        this.partnerName = partnerName;
        this.partnerCode = partnerCode;
        this.customerName = customerName;
        this.customerCity = customerCity;
        this.customerAddress = customerAddress;
        this.customerAddressNo = customerAddressNo;
        this.customerPhone = customerPhone;
        this.customerWarning = customerWarning;
        this.productName = productName;
        this.productCode = productCode;
        this.productModel = productModel;
        this.plannedDate = plannedDate;
        this.statusText = statusText;
    }

    public Route(){

    }

    public int getWorkOrderResultID() {
        return workOrderResultID;
    }

    public void setWorkOrderResultID(int workOrderResultID) {
        this.workOrderResultID = workOrderResultID;
    }

    public int getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(int workOrderID) {
        this.workOrderID = workOrderID;
    }

    public String getWorkOrderCode() {
        return workOrderCode;
    }

    public void setWorkOrderCode(String workOrderCode) {
        this.workOrderCode = workOrderCode;
    }

    public String getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(String workOrderType) {
        this.workOrderType = workOrderType;
    }

    public boolean isInWarranty() {
        return inWarranty;
    }

    public void setInWarranty(boolean inWarranty) {
        this.inWarranty = inWarranty;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerAddress() {
        return partnerAddress;
    }

    public void setPartnerAddress(String partnerAddress) {
        this.partnerAddress = partnerAddress;
    }

    public String getPartnerContact() {
        return partnerContact;
    }

    public void setPartnerContact(String partnerContact) {
        this.partnerContact = partnerContact;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
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

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public boolean isCustomerWarning() {
        return customerWarning;
    }

    public void setCustomerWarning(boolean customerWarning) {
        this.customerWarning = customerWarning;
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

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductPurchaseDate() {
        return productPurchaseDate;
    }

    public void setProductPurchaseDate(String productPurchaseDate) {
        this.productPurchaseDate = productPurchaseDate;
    }

    public String getProductProductionDate() {
        return productProductionDate;
    }

    public void setProductProductionDate(String productProductionDate) {
        this.productProductionDate = productProductionDate;
    }

    public String getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(String plannedDate) {
        this.plannedDate = plannedDate;
    }

    public int getWorkOrderPollID() {
        return workOrderPollID;
    }

    public void setWorkOrderPollID(int workOrderPollID) {
        this.workOrderPollID = workOrderPollID;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getWorkOrderNote() {
        return workOrderNote;
    }

    public void setWorkOrderNote(String workOrderNote) {
        this.workOrderNote = workOrderNote;
    }

    public String getWorkOrderDescription() {
        return workOrderDescription;
    }

    public void setWorkOrderDescription(String workOrderDescription) {
        this.workOrderDescription = workOrderDescription;
    }

    public String getFailureCauseNote() {
        return failureCauseNote;
    }

    public void setFailureCauseNote(String failureCauseNote) {
        this.failureCauseNote = failureCauseNote;
    }

    public boolean isLegalPerson() {
        return isLegalPerson;
    }

    public void setLegalPerson(boolean legalPerson) {
        isLegalPerson = legalPerson;
    }

    public boolean isEscalated() {
        return isEscalated;
    }

    public void setEscalated(boolean escalated) {
        isEscalated = escalated;
    }

    public boolean isSerialNoScanned() {
        return isSerialNoScanned;
    }

    public void setSerialNoScanned(boolean serialNoScanned) {
        isSerialNoScanned = serialNoScanned;
    }

    public boolean isHasSignature() {
        return hasSignature;
    }

    public void setHasSignature(boolean hasSignature) {
        this.hasSignature = hasSignature;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public int getClosedStatus() {
        return closedStatus;
    }

    public void setClosedStatus(int closedStatus) {
        this.closedStatus = closedStatus;
    }

    public List<Service> getWorkOrderServiceList() {
        return workOrderServiceList;
    }

    public void setWorkOrderServiceList(List<Service> workOrderServiceList) {
        this.workOrderServiceList = workOrderServiceList;
    }

    public List<AddedFailuresAndCauses> getWorkOrderFailureList() {
        return workOrderFailureList;
    }

    public void setWorkOrderFailureList(List<AddedFailuresAndCauses> workOrderFailureList) {
        this.workOrderFailureList = workOrderFailureList;
    }

    public List<Material> getWorkOrderMaterialList() {
        return workOrderMaterialList;
    }

    public void setWorkOrderMaterialList(List<Material> workOrderMaterialList) {
        this.workOrderMaterialList = workOrderMaterialList;
    }

    public List<PollQuestion> getWorkOrderPollList() {
        return workOrderPollList;
    }

    public void setWorkOrderPollList(List<PollQuestion> workOrderPollList) {
        this.workOrderPollList = workOrderPollList;
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

    public boolean isCustomerRejected() {
        return isCustomerRejected;
    }

    public void setCustomerRejected(boolean customerRejected) {
        isCustomerRejected = customerRejected;
    }
}

