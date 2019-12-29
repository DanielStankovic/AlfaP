package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkOrderResult {

    @SerializedName("WorkOrderID")
    private int workOrderID;
    @SerializedName("ProductID")
    private int productID;
    @SerializedName("Power")
    private int power;
    @SerializedName("SerialNumber")
    private String serialNumber;
    @SerializedName("PurchaseDate")
    private String purchaseDate;
    @SerializedName("ProductionDate")
    private String productionDate;
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
    @SerializedName("ResultNote")
    private String resultNote;
    @SerializedName("ResultDescription")
    private String resultDescription;
    @SerializedName("FailureCauseNote")
    private String failureCauseNote;
    @SerializedName("SignatureNote")
    private String signatureNote;
    @SerializedName("IsEscalated")
    private boolean isEscalated;
    @SerializedName("IsSerialNumberScanned")
    private boolean isSerialNumberScanned;
    @SerializedName("HasSignature")
    private boolean hasSignature;
    @SerializedName("IsClosed")
    private boolean isClosed;
    @SerializedName("CreatedDate")
    private String createdDate;
    @SerializedName("WorkOrderServiceResult")
    private List<Service> workOrderServiceResult;
    @SerializedName("WorkOrderMaterialResult")
    private List<Material> workOrderMaterialResult;
    @SerializedName("WorkOrderFailureResult")
    private List<AddedFailuresAndCauses> workOrderFailureResult;
    @SerializedName("WorkOrderPollQuestionResult")
    private List<PollQuestion> workOrderPollQuestionResult;
    @SerializedName("IsLegalPersion")
    private boolean isLegalPersion;
    @SerializedName("InWarranty")
    private boolean inWarranty;
    @SerializedName("IsCustomerRejected")
    private boolean isCustomerRejected;

    private int workOrderResultID;

    public WorkOrderResult(int workOrderID, int productID, int power, String serialNumber,
                           String purchaseDate, String productionDate, String resultNote,
                           String resultDescription, String signatureNote, boolean isEscalated,
                           boolean isSerialNumberScanned, boolean hasSignature, boolean isClosed,
                           String createdDate,
                           List<Service> workOrderServiceResult,
                           List<Material> workOrderMaterialResult,
                           List<AddedFailuresAndCauses> workOrderFailureResult) {
        this.workOrderID = workOrderID;
        this.productID = productID;
        this.power = power;
        this.serialNumber = serialNumber;
        this.purchaseDate = purchaseDate;
        this.productionDate = productionDate;
        this.resultNote = resultNote;
        this.resultDescription = resultDescription;
        this.signatureNote = signatureNote;
        this.isEscalated = isEscalated;
        this.isSerialNumberScanned = isSerialNumberScanned;
        this.hasSignature = hasSignature;
        this.isClosed = isClosed;
        this.createdDate = createdDate;
        this.workOrderServiceResult = workOrderServiceResult;
        this.workOrderMaterialResult = workOrderMaterialResult;
        this.workOrderFailureResult = workOrderFailureResult;
    }

    public WorkOrderResult() {
    }

    public int getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(int workOrderID) {
        this.workOrderID = workOrderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
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

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getFailureCauseNote() {
        return failureCauseNote;
    }

    public void setFailureCauseNote(String failureCauseNote) {
        this.failureCauseNote = failureCauseNote;
    }

    public String getSignatureNote() {
        return signatureNote;
    }

    public void setSignatureNote(String signatureNote) {
        this.signatureNote = signatureNote;
    }

    public boolean isEscalated() {
        return isEscalated;
    }

    public void setEscalated(boolean escalated) {
        isEscalated = escalated;
    }

    public boolean isSerialNumberScanned() {
        return isSerialNumberScanned;
    }

    public void setSerialNumberScanned(boolean serialNumberScanned) {
        isSerialNumberScanned = serialNumberScanned;
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

    public List<Service> getWorkOrderServiceResult() {
        return workOrderServiceResult;
    }

    public void setWorkOrderServiceResult(List<Service> workOrderServiceResult) {
        this.workOrderServiceResult = workOrderServiceResult;
    }

    public List<Material> getWorkOrderMaterialResult() {
        return workOrderMaterialResult;
    }

    public void setWorkOrderMaterialResult(List<Material> workOrderMaterialResult) {
        this.workOrderMaterialResult = workOrderMaterialResult;
    }

    public List<AddedFailuresAndCauses> getWorkOrderFailureResult() {
        return workOrderFailureResult;
    }

    public void setWorkOrderFailureResult(List<AddedFailuresAndCauses> workOrderFailureResult) {
        this.workOrderFailureResult = workOrderFailureResult;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public List<PollQuestion> getWorkOrderPollQuestionResult() {
        return workOrderPollQuestionResult;
    }

    public void setWorkOrderPollQuestionResult(List<PollQuestion> workOrderPollQuestionResult) {
        this.workOrderPollQuestionResult = workOrderPollQuestionResult;
    }

    public int getWorkOrderResultID() {
        return workOrderResultID;
    }

    public void setWorkOrderResultID(int workOrderResultID) {
        this.workOrderResultID = workOrderResultID;
    }

    public boolean isLegalPersion() {
        return isLegalPersion;
    }

    public void setLegalPersion(boolean legalPersion) {
        isLegalPersion = legalPersion;
    }

    public boolean isInWarranty() {
        return inWarranty;
    }

    public void setInWarranty(boolean inWarranty) {
        this.inWarranty = inWarranty;
    }

    public boolean isCustomerRejected() {
        return isCustomerRejected;
    }

    public void setCustomerRejected(boolean customerRejected) {
        isCustomerRejected = customerRejected;
    }
}
