package com.galaxy_techno.uKnow.model.dto

import com.google.gson.annotations.SerializedName

data class CategoryListDTO(
    val data: CategoryList,
    val message: String,
    val messageCode: Int,
    val status: String
)

data class CategoryItem(
    @SerializedName("category")
    val categoryName: String,
    val categoryId: Int
)

data class CategoryList(
    val categoryList: List<CategoryItem>
)