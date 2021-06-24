package com.openclassrooms.realestatemanager.viewModels;

import com.openclassrooms.realestatemanager.geocodingRetrofitAPI.GeocodingApiService;
import com.openclassrooms.realestatemanager.geocodingRetrofitAPI.pojo.Geocoding;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GeoViewModel {


    public static Observable<Geocoding> FetchGeocode ( String address){
        GeocodingApiService geocodingService = GeocodingApiService.retrofit.create(GeocodingApiService.class);
        return geocodingService.getGeocode(address).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).timeout(10, TimeUnit.SECONDS);
    }





}
