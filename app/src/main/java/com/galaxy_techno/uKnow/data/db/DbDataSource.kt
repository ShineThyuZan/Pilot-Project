package com.galaxy_techno.uKnow.data.db

import com.galaxy_techno.uKnow.model.entity.User

interface DbDataSource {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User
}