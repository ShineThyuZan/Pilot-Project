package com.galaxy_techno.uKnow.model.dto

data class OTPValidateDTO(
    val status: String,
    val messageCode: Int,

    val data: OTPValidateData? = null,
    val message: String? = null,
    val error : String? = null
)


data class OTPValidateData(
    val otpCheckSuccess: Boolean
)


