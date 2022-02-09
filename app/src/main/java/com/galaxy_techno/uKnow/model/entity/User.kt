package com.galaxy_techno.uKnow.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.galaxy_techno.uKnow.common.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constant.USER_TABLE)
data class User(
    @PrimaryKey(autoGenerate = false)
    val sellerId : Int,
    val accountType: Int,
    val baseCountryId: Int,
    val shopName : String,
    val email : String,
    val phone : String
) : Parcelable
