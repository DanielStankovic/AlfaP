package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

public class Draft {

    //NOTE  Ova model klasa se koristi za sve tipove draftove. Neka polja iz radnog naloga koriscena su za ostala polja


    private int workOrderResultID;
    private int workOrderID;

    // NOTE: Koristice se kao CheckInID, Time (start-stop), ImageTypeName,
    private String workOrderCode;

    //NOTE: START/STOP text
    private String productName;

    private String partnerName;

    private String customerName;
    private String customerCity;
    private String customerAddress;

    //NOTE: Koristice se kao CheckInDate
    private String createdDate;

    //NOTE: Koristiec se kao CheckOutDate,
    private String sentDate;

    private boolean isSent;


    private String headerTitle;
    private int itemType;



    public Draft() {
    }


    //NOTE: Ovaj konstruktor sluzi da bi se odmah instancirao heder za grupe
    public Draft(String headerTitle, int itemType) {
        this.headerTitle = headerTitle;
        this.itemType = itemType;
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

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }
}
