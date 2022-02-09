package com.galaxy_techno.uKnow.model.dto

import com.google.gson.annotations.SerializedName

data class SellerMenuListDTO(
    val status: String,
    val messageCode: Int,

    val data: SellerMenuList? = null,
    val message: String? = null,
    val error: String? = null
)

data class SellerMenu(
    @SerializedName("menuId")
    val id: Int,
    @SerializedName("menu")
    val name: String
)

data class SellerMenuList(
    @SerializedName("sellerMenuList")
    val list: List<SellerMenu>
)