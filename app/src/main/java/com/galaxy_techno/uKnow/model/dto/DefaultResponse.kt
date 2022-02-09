package com.galaxy_techno.uKnow.model.dto

data class DefaultResponse(
    val isSuccess : Boolean,
    val status : String,
    val messageCode : Int,

    val message : String? = null,
    val error : String? = null
)

