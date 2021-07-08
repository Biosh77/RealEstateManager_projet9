package com.openclassrooms.realestatemanager.geocodingRetrofitAPI.pojo;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

public class Geometry {
    @SerializedName("location")
    private com.openclassrooms.realestatemanager.geocodingRetrofitAPI.pojo.Location location;
    @SerializedName("location_type")
    private String locationType;
    @SerializedName("viewport")
    private Viewport viewport;

    public com.openclassrooms.realestatemanager.geocodingRetrofitAPI.pojo.Location getLocation() {
        return location;
    }

    public void setLocation(com.openclassrooms.realestatemanager.geocodingRetrofitAPI.pojo.Location location) {
        this.location = location;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
