package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class ProductDocument {

    @SerializedName("ProductDocumentationID")
    private int productDocumentationID;
    @SerializedName("ProductID")
    private int productID;
    @SerializedName("DocumentType")
    private String documentType;
    @SerializedName("DocumentName")
    private String documentName;
    @SerializedName("DocumentCode")
    private String documentCode;
    @SerializedName("ModifiedDate")
    private String modifiedDate;

    private boolean isDownloaded;

    public ProductDocument(int productDocumentationID, int productID, String documentType, String documentName, String documentCode, String modifiedDate) {
        this.productDocumentationID = productDocumentationID;
        this.productID = productID;
        this.documentType = documentType;
        this.documentName = documentName;
        this.documentCode = documentCode;
        this.modifiedDate = modifiedDate;
    }

    public ProductDocument() {
    }

    public int getProductDocumentationID() {
        return productDocumentationID;
    }

    public void setProductDocumentationID(int productDocumentationID) {
        this.productDocumentationID = productDocumentationID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
}
