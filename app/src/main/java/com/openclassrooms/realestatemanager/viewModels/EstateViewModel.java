package com.openclassrooms.realestatemanager.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EstateViewModel extends ViewModel {

    //Repository
    private final EstateDataRepository estateDataSource;
    private final Executor executor;


    public EstateViewModel(EstateDataRepository estateDataSource, Executor executor) {
        this.estateDataSource = estateDataSource;
        this.executor = executor;
    }


    public LiveData<List<Estate>> getEstates() {
        return estateDataSource.getEstates();
    }


    public LiveData<Estate> getEstate(long estateID) {
        return estateDataSource.getEstate(estateID);
    }


    public void createEstate(final Estate estate) {
        executor.execute(() -> {
            estateDataSource.createEstate(estate);
        });
    }


    public void updateEstate(Estate estate) {
        executor.execute(() -> {
            estateDataSource.updateEstate(estate);
        });
    }

    public int deleteEstate(long estateID) {
        return estateDataSource.deleteEstate(estateID);
    }


}