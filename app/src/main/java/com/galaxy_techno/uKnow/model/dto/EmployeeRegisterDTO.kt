package com.galaxy_techno.uKnow.model.dto

data class EmployeeRegisterDTO(
    val status: String,
    val messageCode: Int,

    val data: EmployeeData? = null,
    val message: String? = null,
    val error : String? = null
)

data class EmployeeData(
    val empLoginId: String,
    val password: String,
    val menuAccessList: List<MenuAccess>
)

data class MenuAccess(
    val menuId: Int,
    val menu: String
)