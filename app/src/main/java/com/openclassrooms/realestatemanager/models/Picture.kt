package com.openclassrooms.realestatemanager.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(foreignKeys = [ForeignKey(entity = Estate::class,
parentColumns = ["estateID"],
childColumns = ["idEstate"],
onDelete = CASCADE)])


data class Picture (
        var idEstate :String,
        var picturePath : String,
        var pictureDescription : String,

        ) : Serializable {
    @PrimaryKey(autoGenerate = true) var pictureId : Int = 0
}