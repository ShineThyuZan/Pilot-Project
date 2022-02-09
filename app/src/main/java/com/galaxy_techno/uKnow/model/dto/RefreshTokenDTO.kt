package com.galaxy_techno.uKnow.model.dto

data class RefreshTokenDTO(
    val status: String,
    val messageCode: Int,

    val data: RefreshToken? = null,
    val message: String? = null,
    val error: String? = null
)

data class RefreshToken(
    val refreshToken: String,
    val accessToken: String
)