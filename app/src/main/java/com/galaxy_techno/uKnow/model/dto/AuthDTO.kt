package com.galaxy_techno.uKnow.model.dto

import com.galaxy_techno.uKnow.model.entity.User
import com.google.gson.annotations.Expose

data class LoginDTO(
    val status: String,
    val messageCode: Int,

    val data: UserData? = null,
    val message: String? = null,
    val error: String? = null
)

data class RegisterDTO(
    val status: String,
    val messageCode: Int,

    val data: UserData? = null,
    val message: String? = null,
    val error: String? = null
)

data class UserData(
    val sellerId: Int,
    val accountType: Int,
    val baseCountryId: Int,
    val shopName: String,
    val email: String,
    val mobile: String,
    val password: String,
    @Expose val accessToken: String? = null,
    @Expose val refreshToken: String? = null
)

fun UserData.toEntity(): User {
    return User(
        sellerId = sellerId,
        accountType = accountType,
        baseCountryId = baseCountryId,
        shopName = shopName,
        email = email,
        phone = mobile
    )
}


