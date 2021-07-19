package com.openclassrooms.realestatemanager.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.FullEstate;
import com.openclassrooms.realestatemanager.models.Picture;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.repositories.FullEstateRepository;
import com.openclassrooms.realestatemanager.repositories.PictureDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class EstateViewModel extends ViewModel {

    //Repository
    private final EstateDataRepository estateDataSource;
    PictureDataRepository pictureDataSource;
    private final FullEstateRepository fullEstateDataSource;
    private final Executor executor;


    public EstateViewModel(EstateDataRepository estateDataSource,PictureDataRepository pictureDataSource, FullEstateRepository fullEstateRepository, Executor executor) {
        this.pictureDataSource = pictureDataSource;
        this.estateDataSource = estateDataSource;
        this.fullEstateDataSource = fullEstateRepository;
        this.executor = executor;
    }


    public LiveData<List<Estate>> getEstates() {
        return estateDataSource.getEstates();
    }


    public LiveData<Estate> getEstate(String estateID) {
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



    public LiveData<List<Picture>> getPictures(String idEstate) {
        return pictureDataSource.getPictures(idEstate);
    }

    public LiveData<List<Picture>> getPicture() {
        return pictureDataSource.getPicture();
    }

    public void createPicture(final Picture picture) {
        executor.execute(() -> {
            pictureDataSource.createPicture(picture);
        });
    }


    public void updatePicture(Picture picture) {
        executor.execute(() -> {
            pictureDataSource.updatePicture(picture);
        });
    }


    public int deletePicture(int pictureId) {
        return pictureDataSource.deletePicture(pictureId);
    }

    public LiveData<List<FullEstate>> getFullEstate() {
        return fullEstateDataSource.getFullEstates();
    }


}
