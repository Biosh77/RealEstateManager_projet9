package com.openclassrooms.realestatemanager.database.dao;


import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;

@Dao
public interface EstateDAO {

    @Query("SELECT * FROM Estate ")
    LiveData<List<Estate>> getEstates();

    @Insert
    long insertEstate(Estate estate);

    @Update
    int updateEstate(Estate estate);

    @Query("SELECT * FROM Estate WHERE estateID = :estateID")
    LiveData<Estate> getEstate(long estateID);

    @Query("DELETE FROM Estate WHERE estateID = :estateID")
    int deleteEstate(long estateID);


    //For ContentProvider
    @Query("SELECT * FROM Estate WHERE estateID = :estateID")
    Cursor getEstateWithCursor(long estateID);





   /*
    getEstates ( liste estates )
    getEstate ( pour id ? )
    insertEstate ( pour créer l'estate et récupérer les données après )
    deleteEstate ( pas utilise pour ce projet )
    updateEstate ( pour edit )
    getSearchEstate ( ??? on doit récupérer les données sur la BDD pour les recherches )
 */

}
