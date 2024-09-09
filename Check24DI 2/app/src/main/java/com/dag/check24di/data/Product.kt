package com.dag.check24di.data
import kotlinx.serialization.Serializable

@Serializable
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

@Serializable
data class Price(
    val value: Double,
    val currency: String,
)

