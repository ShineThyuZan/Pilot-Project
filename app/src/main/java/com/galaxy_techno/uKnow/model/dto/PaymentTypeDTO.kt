package com.galaxy_techno.uKnow.model.dto

import com.google.gson.annotations.SerializedName

data class PaymentTypeDTO(
    val status: String,
    val messageCode: Int,

    val data: Data? = null,
    val message: String? = null,
    val error: String? = null
)

data class AccountType(
    @SerializedName("accountTypeId")
    val id: Int,
    @SerializedName("accountType")
    val name: String
)

data class Data(
    @SerializedName("accountTypeList")
    val list: List<AccountType>
)