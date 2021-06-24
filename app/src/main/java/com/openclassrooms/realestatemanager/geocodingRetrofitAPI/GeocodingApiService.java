package com.openclassrooms.realestatemanager.geocodingRetrofitAPI;

import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.geocodingRetrofitAPI.pojo.Geocoding;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodingApiService {


    String GOOGLE_MAP_API_KEY= BuildConfig.MAP_KEY;

    @GET("maps/api/geocode/json?key="+ GOOGLE_MAP_API_KEY)
    Observable<Geocoding> getGeocode(@Query("address")String address);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
