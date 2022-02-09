package com.galaxy_techno.uKnow.model.req

data class EmployeeRegisterRequest(
    val sellerId: Int,
    val empMobile: String,
    val menuAccessList: List<MenuAccess>,
)

data class MenuAccess(
    val menu: String,
    val menuId: Int
)
