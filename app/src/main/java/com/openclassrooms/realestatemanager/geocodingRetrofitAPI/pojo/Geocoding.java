package com.openclassrooms.realestatemanager.geocodingRetrofitAPI.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Geocoding implements Serializable {

    @SerializedName("results")
    private List<Result> results;
    @SerializedName("status")
    private String status;


    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
