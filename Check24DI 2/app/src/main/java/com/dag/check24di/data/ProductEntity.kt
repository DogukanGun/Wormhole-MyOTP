package com.dag.check24di.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey
    val id: Long,
    val isDeleted:Boolean
) {
    fun getProduct(products:List<Product>):Product {
        return products.first { it.id == this.id }
    }
}
