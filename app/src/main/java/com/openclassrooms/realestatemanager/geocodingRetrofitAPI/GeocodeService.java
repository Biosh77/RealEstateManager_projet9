package com.openclassrooms.realestatemanager.geocodingRetrofitAPI;

import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.geocodingRetrofitAPI.pojo.Geocoding;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeocodeService {


    String GOOGLE_MAP_API_KEY = BuildConfig.MAP_KEY;

    @GET("maps/api/geocode/json?key=AIzaSyBcOk5THpnDnY_8ymJHerkseb-7ZBDR4NM")
    Observable<Geocoding> getGeocode(@Query("address") String address);


    static GeocodeService create() {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


        httpClient.addInterceptor(logging);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(GeocodeService.class);
    }

}
