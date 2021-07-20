package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.openclassrooms.realestatemanager.database.dao.EstateDAO;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.FullEstate;

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

    public LiveData<Estate> getEstate(String estateID) {
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

    // --- SEARCH ---

    public LiveData<List<FullEstate>> getSearchEstates(String queryString, List<Object> args){
        SupportSQLiteQuery query = new SimpleSQLiteQuery(queryString,args.toArray());
        return estateDAO.getSearchEstates(query);
    }

}
