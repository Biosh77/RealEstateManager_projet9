package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.EstateDAO;
import com.openclassrooms.realestatemanager.database.dao.FullEstateDAO;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.FullEstate;

import java.util.List;

public class FullEstateRepository {

    private FullEstateDAO fullEstateDAO;

    public FullEstateRepository(FullEstateDAO fullEstateDAO) {
        this.fullEstateDAO = fullEstateDAO;
    }


    public LiveData<List<FullEstate>> getFullEstates() {
        return this.fullEstateDAO.getAllData();
    }

}
