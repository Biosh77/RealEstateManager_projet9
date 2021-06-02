package com.openclassrooms.realestatemanager.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.openclassrooms.realestatemanager.viewModels.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.Estate;

public class DetailFragment extends Fragment implements OnMapReadyCallback {

    private EstateViewModel estateViewModel;
    //private Estate estate;

    private GoogleMap map;


    private MapView mapView;
    private TextView description;
    private TextView surface;
    private TextView rooms;
    private TextView bathrooms;
    private TextView bedrooms;
    private TextView address;
    private TextView postalCode;
    private TextView city;

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
    }


    public void updateUi(Estate estate){

        if (estate != null){

            description.setText(estate.getDescription());
            description.setEnabled(false);

            surface.setText(estate.getSurface());
            surface.setEnabled(false);

            rooms.setText(estate.getRooms());
            rooms.setEnabled(false);

            bathrooms.setText(estate.getBathrooms());
            bathrooms.setEnabled(false);

            bedrooms.setText(estate.getBedrooms());
            bedrooms.setEnabled(false);

            address.setText(estate.getAddress());
            address.setEnabled(false);

            postalCode.setText(estate.getPostalCode());
            postalCode.setEnabled(false);

            city.setText(estate.getCity());
            city.setEnabled(false);

            //photos list


        }

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.zoomBy(12));


    }


}
