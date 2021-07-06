package com.openclassrooms.realestatemanager.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.Picture;
import com.openclassrooms.realestatemanager.repositories.PictureDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class PictureViewModel extends ViewModel {

    PictureDataRepository pictureDataSource;
    Executor executor;


    public PictureViewModel(PictureDataRepository pictureDataSource, Executor executor) {
        this.pictureDataSource = pictureDataSource;
        this.executor = executor;
    }


    public LiveData<List<Picture>> getPictures(int idEstate) {
        return pictureDataSource.getPictures(idEstate);
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


}
