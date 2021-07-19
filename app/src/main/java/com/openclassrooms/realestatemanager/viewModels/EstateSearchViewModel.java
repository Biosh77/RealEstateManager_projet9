package com.openclassrooms.realestatemanager.viewModels;

import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.SearchEstates;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class EstateSearchViewModel extends ViewModel {


    EstateDataRepository estateDataSource;
    Executor executor;

    public EstateSearchViewModel(EstateDataRepository estateDataSource, Executor executor) {
        this.estateDataSource = estateDataSource;
        this.executor = executor;
    }

    public LiveData<List<Estate>> searchEstate(

            String estateType,
            String city,
            Integer minRooms,
            Integer maxRooms,
            Integer minSurface,
            Integer maxSurface,
            Double minPrice,
            Double maxPrice,
            String minDate,
            String maxDate,
            Boolean photos,
            Boolean schools,
            Boolean stores,
            Boolean park,
            Boolean restaurants,
            Boolean sold

    ) {
        String queryString = "";
        List<Object> args = new ArrayList<>();
        boolean containsCondition = false;

        queryString += "SELECT * FROM Estate";

        if (!estateType.isEmpty()) {
            queryString += " WHERE";
            containsCondition = true;
            queryString += " estateType=?";
            args.add(estateType);
        }
        if (!city.isEmpty()) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }
            queryString += " city=?";
            args.add(city);
        }
        if (minRooms != null && maxRooms != null && minRooms >= 0 && maxRooms > 0) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }
            queryString += " rooms BETWEEN ? AND ?";
            args.add(minRooms);
            args.add(maxRooms);
        }
        if (minSurface != null && maxSurface != null && minSurface >= 0 && maxSurface > 0) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
            }
            queryString += " surface BETWEEN ? AND ?";
            args.add(minSurface);
            args.add(maxSurface);
        }
        if (minPrice != null && maxPrice != null && minPrice >= 0 && maxPrice > 0) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
            }
            queryString += " price BETWEEN ? AND ?";
            args.add(minPrice);
            args.add(maxPrice);
        }
        if (minDate != null && maxDate != null ) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";

            }
            queryString += " entryDateEstate BETWEEN ? AND ?";
            args.add(minDate);
            args.add(maxDate);

        }
        if (photos.equals(true)) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
            }
            queryString += " photoList <> ''";
        }
        if (schools.equals(true)) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }
            queryString += " schools = 1";
        }
        if (stores.equals(true)) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }
            queryString += " stores = 1";
        }

        if (park.equals(true)) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }
            queryString += " park = 1";
        }

        if (restaurants.equals(true)) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }
            queryString += " restaurants = 1";
        }

        if (sold.equals(true)) {
            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
            }
            queryString += " sold = 1";
        }

        return estateDataSource.getSearchEstates(queryString, args);
    }


}
