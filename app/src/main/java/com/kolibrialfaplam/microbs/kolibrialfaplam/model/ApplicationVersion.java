package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class ApplicationVersion {

    @SerializedName("ApplicationVersionID")
    private int applicationVersionID;
    @SerializedName("VersionCode")
    private int versionCode;
    @SerializedName("VersionName")
    private String versionName;
    @SerializedName("Description")
    private String description;
    @SerializedName("DownloadLink")
    private String downloadLink;
    @SerializedName("CreatedDate")
    private String createdDate;

    public ApplicationVersion(int applicationVersionID, int versionCode, String versionName,
                              String description, String downloadLink, String createdDate) {
        this.applicationVersionID = applicationVersionID;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.description = description;
        this.downloadLink = downloadLink;
        this.createdDate = createdDate;
    }

    public int getApplicationVersionID() {
        return applicationVersionID;
    }

    public void setApplicationVersionID(int applicationVersionID) {
        this.applicationVersionID = applicationVersionID;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
