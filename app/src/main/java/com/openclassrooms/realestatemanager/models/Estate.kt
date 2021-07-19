package com.openclassrooms.realestatemanager.models

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
data class Estate(
        @PrimaryKey
        var estateID: String = "" ,

        var estateType: String? = "",
        var surface: Int? = null,
        var rooms: Int?= null,
        var bedrooms: Int?= null,
        var bathrooms: Int?= null,
        var price: Double?= null,
        var description: String? = "",
        var address: String? = "",
        var postalCode: Int? = null,
        var city: String?="",
        var schools: Boolean = false,
        var stores: Boolean = false,
        var park: Boolean = false,
        var restaurants: Boolean = false,
        var sold: Boolean = false,
        var entryDateEstate: String? = "",
        var soldDate: String? = "",
        var agentName: String = "",

) : Serializable

fun fromContentValues(values: ContentValues): Estate {



    val estate = Estate()
    if (values.containsKey("id")) estate.estateID = values.getAsString("id")
    if (values.containsKey("estateType")) estate.estateType = values.getAsString("estateType")
    if (values.containsKey("surface")) estate.surface = values.getAsInteger("surface")
    if (values.containsKey("rooms")) estate.rooms = values.getAsInteger("rooms")
    if (values.containsKey("bedrooms")) estate.bedrooms = values.getAsInteger("bedrooms")
    if (values.containsKey("bathrooms")) estate.bathrooms = values.getAsInteger("bathrooms")
    if (values.containsKey("price")) estate.price = values.getAsDouble("price")
    if (values.containsKey("description")) estate.description = values.getAsString("description")
    if (values.containsKey("address")) estate.address = values.getAsString("address")
    if (values.containsKey("postalCode")) estate.postalCode = values.getAsInteger("postalCode")
    if (values.containsKey("city")) estate.city = values.getAsString("city")
    if (values.containsKey("schools")) estate.schools = values.getAsBoolean("schools")
    if (values.containsKey("stores")) estate.stores = values.getAsBoolean("stores")
    if (values.containsKey("park")) estate.park = values.getAsBoolean("park")
    if (values.containsKey("restaurants")) estate.restaurants = values.getAsBoolean("restaurants")
    if (values.containsKey("entryDateEstate")) estate.entryDateEstate = values.getAsString("entryDateEstate")
    if (values.containsKey("soldDate")) estate.soldDate = values.getAsString("soldDate")
    if (values.containsKey("agentName")) estate.agentName = values.getAsString("agentName")

    return estate
}
