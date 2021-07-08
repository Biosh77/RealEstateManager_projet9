package com.openclassrooms.realestatemanager.utils;

import com.openclassrooms.realestatemanager.geocodingRetrofitAPI.GeocodeService;
import com.openclassrooms.realestatemanager.geocodingRetrofitAPI.pojo.Geocoding;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GeocodeStream {


    public static Observable<Geocoding> FetchGeocode ( String address){
        GeocodeService geocodingService = GeocodeService.create();
        return geocodingService.getGeocode(address).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).timeout(10, TimeUnit.SECONDS);
    }





}
