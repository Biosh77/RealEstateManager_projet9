package com.openclassrooms.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.openclassrooms.realestatemanager.models.Picture


@Dao
interface PictureDAO {

    @Query("SELECT * FROM Picture WHERE idEstate = :idEstate")
    fun getPictures(idEstate:Int): LiveData<List<Picture>>

    @Insert
    fun insertPicture(picture: Picture)

    @Update
    fun updatePicture(picture: Picture)

    @Query("DELETE FROM Picture WHERE pictureId = :pictureId")
    fun deletePicture(pictureId: Int) : Int
}