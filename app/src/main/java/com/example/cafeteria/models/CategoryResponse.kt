package com.example.cafeteria.models

import java.io.Serializable

data class CategoryRequest(
    var imageUrl: String ?=null,
    var name: String ?=null
):Serializable

data class CategoryResponse(
    var id: Int?=null,
    var imageUrl: String?=null,
    var name: String?=null,
    var products: List<Any>?=null
): Serializable
