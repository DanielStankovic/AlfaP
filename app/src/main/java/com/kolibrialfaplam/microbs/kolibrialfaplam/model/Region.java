package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class Region {

    @SerializedName("RegionID")
    private int regionID;
    @SerializedName("RegionName")
    private String regionName;
    @SerializedName("RegionCode")
    private String regionCode;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public Region(int regionID, String regionName, String regionCode,
                  String modifiedDate, boolean isActive) {
        this.regionID = regionID;
        this.regionName = regionName;
        this.regionCode = regionCode;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public Region() {
    }

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
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
