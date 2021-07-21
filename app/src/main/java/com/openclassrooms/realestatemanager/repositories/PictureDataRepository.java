package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PictureDAO;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.Picture;

import java.util.List;

public class PictureDataRepository {

    private final PictureDAO pictureDAO;

    public PictureDataRepository(PictureDAO pictureDAO) {
        this.pictureDAO = pictureDAO;
    }

    // --- GET ---

    public LiveData<List<Picture>> getPictures(String  idEstate) {
        return this.pictureDAO.getPictures(idEstate);
    }

    public LiveData<List<Picture>> getPicture() {
        return this.pictureDAO.getPicture();
    }

    // --- CREATE ---

    public void createPicture(Picture picture) {
        pictureDAO.insertPicture(picture);
    }

    // --- UPDATE ---

    public void updatePicture(Picture picture) {
        pictureDAO.updatePicture(picture);
    }

    // --- DELETE ---

    public void deletePicture(int pictureId) {
         this.pictureDAO.deletePicture(pictureId);
    }

}
