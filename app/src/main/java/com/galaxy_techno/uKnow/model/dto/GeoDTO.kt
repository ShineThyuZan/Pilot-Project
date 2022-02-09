package com.galaxy_techno.uKnow.model.dto

import com.google.gson.annotations.SerializedName

//country
data class CountryListDTO(
    val status: String,
    val messageCode: Int,

    val data: CountryList? = null,
    val message: String? = null,
    val error: String? = null
)

//state
data class StateListDTO(
    val status: String,
    val messageCode: Int,

    val data: StateList? = null,
    val message: String? = null,
    val error: String? = null
)

//township
data class TownshipListDTO(
    val status: String,
    val messageCode: Int,

    val data: TownshipList? = null,
    val message: String? = null,
    val error: String? = null
)

//State with Township
data class StateWithTownshipDTO(
    val status: String,
    val messageCode: Int,

    val data: List<StateWithTownshipList>? = null,
    val message: String? = null,
    val error: String? = null
)

//List
data class CountryList(
    @SerializedName("countryList")
    val list: List<CountryItem>
)

data class StateList(
    @SerializedName("stateList")
    val list: List<StateItem>
)

data class TownshipList(
    @SerializedName("townshipList")
    val list: List<TownshipItem>
)

data class StateWithTownshipList(
    val countryId: Int,
    val stateId: Int,
    @SerializedName("state") val stateName: String,
    @SerializedName("townships") val townships: List<TownshipItem>,
    val delete: Boolean
)

//Items
data class CountryItem(
    @SerializedName("countryId") val id: Int,
    @SerializedName("countryCode") val code: String,
    @SerializedName("country") val name: String,
    val isDelete: Boolean
)

data class StateItem(
    val countryId: Int,
    @SerializedName("stateId") val id: Int,
    @SerializedName("state") val name: String,
    val isDelete: Boolean
)

data class TownshipItem(
    val stateId: Int,
    @SerializedName("townshipId") val id: Int,
    @SerializedName("township") val name: String,
    val isDelete: Boolean
)


