package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class RegionPlace {

    @SerializedName("RegionPlaceID")
    private int regionPlaceID;
    @SerializedName("RegionID")
    private int regionID;
    @SerializedName("RegionPlaceName")
    private String regionPlaceName;
    @SerializedName("RegionPlaceCode")
    private String regionPlaceCode;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public RegionPlace(int regionPlaceID, int regionID, String regionPlaceName,
                       String regionPlaceCode, String modifiedDate, boolean isActive) {
        this.regionPlaceID = regionPlaceID;
        this.regionID = regionID;
        this.regionPlaceName = regionPlaceName;
        this.regionPlaceCode = regionPlaceCode;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public RegionPlace() {
    }

    public int getRegionPlaceID() {
        return regionPlaceID;
    }

    public void setRegionPlaceID(int regionPlaceID) {
        this.regionPlaceID = regionPlaceID;
    }

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }

    public String getRegionPlaceName() {
        return regionPlaceName;
    }

    public void setRegionPlaceName(String regionPlaceName) {
        this.regionPlaceName = regionPlaceName;
    }

    public String getRegionPlaceCode() {
        return regionPlaceCode;
    }

    public void setRegionPlaceCode(String regionPlaceCode) {
        this.regionPlaceCode = regionPlaceCode;
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
