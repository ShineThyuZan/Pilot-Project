package com.galaxy_techno.uKnow.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.galaxy_techno.uKnow.common.Constant
import com.galaxy_techno.uKnow.model.entity.User

@Dao
interface UserDao {
    //todo : abstraction about Data Access Object
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM ${Constant.USER_TABLE}")
    suspend fun retrieveUser() : User

}