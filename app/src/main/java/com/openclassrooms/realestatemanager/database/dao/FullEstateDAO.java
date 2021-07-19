package com.openclassrooms.realestatemanager.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.openclassrooms.realestatemanager.models.FullEstate;

import java.util.List;

@Dao
public interface FullEstateDAO {

    @Transaction
    @Query("SELECT * FROM Estate")
    public LiveData <List<FullEstate>> getAllData();
}
