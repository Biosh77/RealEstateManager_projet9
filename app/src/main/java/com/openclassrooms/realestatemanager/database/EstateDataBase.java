package com.openclassrooms.realestatemanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.openclassrooms.realestatemanager.database.dao.EstateDAO;
import com.openclassrooms.realestatemanager.database.dao.FullEstateDAO;
import com.openclassrooms.realestatemanager.database.dao.PictureDAO;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.FullEstate;
import com.openclassrooms.realestatemanager.models.Picture;


@Database(entities = {Estate.class,Picture.class}, version = 1, exportSchema = false)
public abstract class EstateDataBase extends RoomDatabase  {



    // --- SINGLETON --- // une seule instance pour toute l'app
    private static volatile EstateDataBase INSTANCE;  // pour utiliser la même variable sans copie, 1 pour tout les threads

    // --- DAO ---
    public abstract EstateDAO estateDAO();
    public abstract PictureDAO pictureDAO();
    public abstract FullEstateDAO fullEstateDAO();


    // --- INSTANCE ---
    public static EstateDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (EstateDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EstateDataBase.class, "MyEstateDatabase.db")
                            .build();
                }

            }
        }
        return INSTANCE;
    }

}
