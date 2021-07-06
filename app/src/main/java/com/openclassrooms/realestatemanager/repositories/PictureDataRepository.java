package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.dao.PictureDAO;
import com.openclassrooms.realestatemanager.models.Picture;

import java.util.List;

public class PictureDataRepository {

    private final PictureDAO pictureDAO;

    public PictureDataRepository(PictureDAO pictureDAO) {
        this.pictureDAO = pictureDAO;
    }

    // --- GET ---

    public LiveData<List<Picture>> getPictures(int idEstate) {
        return this.pictureDAO.getPictures(idEstate);
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

    public int deletePicture(int pictureId) {
        return this.pictureDAO.deletePicture(pictureId);
    }

}
