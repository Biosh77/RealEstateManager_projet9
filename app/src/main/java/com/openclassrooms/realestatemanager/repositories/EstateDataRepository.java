package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.EstateDAO;
import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;

public class EstateDataRepository {

    private final EstateDAO estateDAO;

    public EstateDataRepository(EstateDAO estateDAO) {
        this.estateDAO = estateDAO;
    }

    // --- GET ---

    public LiveData<List<Estate>> getEstates() {
        return this.estateDAO.getEstates();
    }

    //--- GET ID ---

    public LiveData<Estate> getEstate(long estateID) {
        return this.estateDAO.getEstate(estateID);
    }

    // --- CREATE ---

    public void createEstate(Estate estate) {
        estateDAO.insertEstate(estate);
    }


    // --- UPDATE ---

    public void updateEstate(Estate estate) {
        estateDAO.updateEstate(estate);
    }

    // --- DELETE ---

    public int deleteEstate(long estateID){
        return this.estateDAO.deleteEstate(estateID);
    }
}
