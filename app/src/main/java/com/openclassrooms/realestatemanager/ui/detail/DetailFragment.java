package com.openclassrooms.realestatemanager.ui.detail;

import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.geocodingRetrofitAPI.pojo.Geocoding;
import com.openclassrooms.realestatemanager.geocodingRetrofitAPI.pojo.Result;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewModels.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.viewModels.GeoViewModel;

import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.internal.Util;

public class DetailFragment extends Fragment implements OnMapReadyCallback {

    private EstateViewModel estateViewModel;


    private GoogleMap map;
    private String completeAddress;
    private Disposable mDisposable;
    private List<Result> result;
    private Marker positionMarker;
    private Estate estate;


    private MapView mapView;
    private TextView description;
    private TextView surface;
    private TextView rooms;
    private TextView bathrooms;
    private TextView bedrooms;
    private TextView address;
    private TextView postalCode;
    private TextView city;
    private TextView agent;
    private ImageView store;
    private ImageView restaurant;
    private ImageView park;
    private ImageView school;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        description = rootView.findViewById(R.id.description_text);
        surface = rootView.findViewById(R.id.surface_text);
        rooms = rootView.findViewById(R.id.room_text);
        bathrooms = rootView.findViewById(R.id.bath_text);
        bedrooms = rootView.findViewById(R.id.bed_text);
        address = rootView.findViewById(R.id.address_text);
        postalCode = rootView.findViewById(R.id.postal_text);
        city = rootView.findViewById(R.id.city_text);
        mapView = rootView.findViewById(R.id.mapView);
        agent = rootView.findViewById(R.id.agent_text);
        store = rootView.findViewById(R.id.store);
        restaurant = rootView.findViewById(R.id.restaurant);
        park = rootView.findViewById(R.id.park);
        school = rootView.findViewById(R.id.school);


        // MAP
        GoogleMapOptions options = new GoogleMapOptions();
        options.liteMode(true);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        configureViewModel();
        configureRecyclerView();


        return rootView;
    }


    private void configureRecyclerView() {
    }

    private void configureViewModel() {
        estateViewModel = new ViewModelProvider(this, Injection.provideViewModelFactory(getContext())).get(EstateViewModel.class);

        int estateId =  getActivity().getIntent().getIntExtra("estate",0);


        if (estateId != 0) {
            this.estateViewModel.getEstate(estateId).observe(getActivity(),
                    (Estate estate) -> {
                        DetailFragment.this.updateUi(estate);
                        String address = estate.getAddress();
                        String postalCode = Objects.requireNonNull(estate.getPostalCode()).toString();
                        String city = estate.getCity();
                        completeAddress = address + "," + postalCode + "," + city;

                        executeHttpRequestWithRetrofit();

                    });
        }
    }


    public void updateUi(Estate estate){

        if (estate != null){

            description.setText(estate.getDescription());
            description.setEnabled(false);

            surface.setText(String.valueOf(estate.getSurface()));
            surface.setEnabled(false);

            rooms.setText(String.valueOf(estate.getRooms()));
            rooms.setEnabled(false);

            bathrooms.setText(String.valueOf(estate.getBathrooms()));
            bathrooms.setEnabled(false);

            bedrooms.setText(String.valueOf(estate.getBedrooms()));
            bedrooms.setEnabled(false);

            address.setText(estate.getAddress());
            address.setEnabled(false);

            postalCode.setText(String.valueOf(estate.getPostalCode()));
            postalCode.setEnabled(false);

            city.setText(estate.getCity());
            city.setEnabled(false);

            agent.setText(estate.getAgentName());
            agent.setEnabled(false);

            if (estate.getStores()){
                store.setVisibility(View.VISIBLE);
            }
            if (estate.getPark()){
                park.setVisibility(View.VISIBLE);
            }
            if (estate.getRestaurants()){
                restaurant.setVisibility(View.VISIBLE);
            }
            if (estate.getSchools()){
               school.setVisibility(View.VISIBLE);
            }






            //photos list


        }


    }




    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (Utils.isInternetAvailable(getContext())) {
            map = googleMap;
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().setMapToolbarEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.zoomBy(12));
        }else {
            Toast.makeText(getContext(),"No internet", Toast.LENGTH_SHORT).show();
        }


    }


    private void executeHttpRequestWithRetrofit() {

        mDisposable = GeoViewModel.FetchGeocode(completeAddress)
                .subscribeWith(new DisposableObserver<Geocoding>() {


                    @Override
                    public void onNext(Geocoding geocoding)  {
                        Log.d("executeHttp", "executeHttp : " + geocoding.getResults());
                        result = geocoding.getResults();

                    }

                    @Override
                    public void onComplete() {
                        if (completeAddress != null) {
                            positionMarker(result);

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    private void dispose(){
        mDisposable.dispose();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispose();
    }

    /**
     * For retrieve estate position with LatLng and marker
     *
     * @param resultGeocoding
     */
    public void positionMarker(List<Result> resultGeocoding) {
        map.clear();
        for (Result geo : resultGeocoding) {

            LatLng latLng = new LatLng(geo.getGeometry().getLocation().getLatitude(),
                    geo.getGeometry().getLocation().getLongitude()
            );
            positionMarker = map.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            positionMarker.showInfoWindow();
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
