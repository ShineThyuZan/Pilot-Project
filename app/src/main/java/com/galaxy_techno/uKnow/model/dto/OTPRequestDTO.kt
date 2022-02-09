package com.galaxy_techno.uKnow.model.dto

data class OTPRequestDTO(
    val status: String,
    val messageCode: Int,

    val data: OTPRequestData? = null,
    val message: String? = null,
    val error : String? = null
)

data class OTPRequestData(
    val expireTimeMin: Int,
    val otpCode: String,
    val isRegister : Boolean
)



