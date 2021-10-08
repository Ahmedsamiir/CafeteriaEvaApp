package com.example.cafeteria.models

import java.io.Serializable

data class ProductRequest(
    var categoryId: Int?=null,
    var imageUrl: String?=null,
    var inOffers: Boolean?=null,
    var name: String?=null,
    var price: Double?=null
):Serializable

data class ProductResponse(
    val category: String? = null,
    val categoryId: Int? = null,
    val id: Int? = null,
    val imageUrl: String? = null,
    val inOffers: Boolean? = null,
    val name: String? = null,
    val price: Double? = null,
    val description: String? = null
) : Serializable

