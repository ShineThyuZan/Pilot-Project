package com.galaxy_techno.uKnow.data.db

import com.galaxy_techno.uKnow.model.entity.User
import javax.inject.Inject

open class DbDataSourceImpl @Inject constructor(
    private val db : UserDatabase
) : DbDataSource {
    override suspend fun saveUser(user: User) {
        db.getDao().insertUser(user)
    }

    override suspend fun getUser(): User {
        return db.getDao().retrieveUser()
    }
}