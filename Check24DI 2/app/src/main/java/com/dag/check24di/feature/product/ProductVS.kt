package com.dag.check24di.feature.product

import com.dag.check24di.data.Product

data class ProductScreenState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val buttonText: String = "Vormerken",
    val isFavorite: Boolean = false,
    val error:Boolean = false,
    val notFound:Boolean = false
)

enum class ButtonText(val text: String) {
    AddToFavorite("Vormerken"),
    RemoveFromFavorite("Vergessen");
}