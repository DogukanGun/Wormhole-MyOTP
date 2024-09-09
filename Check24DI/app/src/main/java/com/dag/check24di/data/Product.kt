package com.dag.check24products.data

data class Product(
    val name: String,
    val type: String,
    val id: Long,
    val color: String,
    val imageURL: String,
    val colorCode: String,
    val available: Boolean,
    val releaseDate: Long,
    val description: String,
    val longDescription: String,
    val rating: Double,
    val price: Price,
)

data class Price(
    val value: Double,
    val currency: String,
)

