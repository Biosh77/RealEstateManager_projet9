package com.openclassrooms.realestatemanager.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FullEstate {

    @Embedded
    public Estate estate;
    @Relation(
            parentColumn = "estateID",
            entityColumn = "idEstate"
    )
    public List<Picture> myListPictures;

}
