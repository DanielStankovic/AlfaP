package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class Partner {

    @SerializedName("PartnerID")
    private int partnerID;
    @SerializedName("PartnerName")
    private String partnerName;
    @SerializedName("PartnerCode")
    private String partnerCode;
    @SerializedName("Address")
    private String address;
    @SerializedName("Contact")
    private String contact;
    @SerializedName("PIB")
    private String pib;
    @SerializedName("IsLegal")
    private boolean isLegal;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public Partner(int partnerID, String partnerName, String partnerCode, String address,
                   String contact, String pib, boolean isLegal, String modifiedDate, boolean isActive) {
        this.partnerID = partnerID;
        this.partnerName = partnerName;
        this.partnerCode = partnerCode;
        this.address = address;
        this.contact = contact;
        this.pib = pib;
        this.isLegal = isLegal;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public Partner() {
    }

    public int getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(int partnerID) {
        this.partnerID = partnerID;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public boolean isLegal() {
        return isLegal;
    }

    public void setLegal(boolean legal) {
        isLegal = legal;
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
