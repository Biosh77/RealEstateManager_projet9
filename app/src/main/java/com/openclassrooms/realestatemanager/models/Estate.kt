package com.openclassrooms.realestatemanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Estate (@PrimaryKey(autoGenerate = true)

        val estateID: Int,
        var estateType: String?,
        var surface: Int?,
        var rooms: Int?,
        var bedrooms: Int?,
        var bathrooms: Int?,
        var ground: Int?,
        var price: Double?,
        var description: String?,
        var address: String?,
        var postalCode: Int?,
        var city: String?,
        var schools: Boolean,
        var stores: Boolean,
        var park: Boolean,
        var restaurants: Boolean,
        var sold: Boolean,
        var entryDateEstate: Long?,
        var soldDate: String?,
        var agentName: String,
        //var photoList: ,

)



