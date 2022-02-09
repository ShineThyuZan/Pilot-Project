package com.galaxy_techno.uKnow.model.req

data class DefaultRequest(
    val id : Int,
    val status : String,
    val messageCode : Int,

    val message : String? = null,
    val error : String? = null
)
