package com.dag.check24di.feature.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dag.check24di.dao.ProductDatabase
import com.dag.check24di.data.Product
import com.dag.check24di.data.ProductEntity
import com.dag.check24di.feature.home.HomeScreenState
import com.dag.check24di.feature.home.HomeVM.Companion.HOME_VM_LOG
import com.dag.check24di.retrofit.ApiSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductVM constructor(
    private val apiSource: ApiSource,
    private val productDatabase: ProductDatabase
) : ViewModel() {

    private var _viewState: MutableStateFlow<ProductScreenState> =
        MutableStateFlow(ProductScreenState())
    val viewState: StateFlow<ProductScreenState> = _viewState.asStateFlow()

    //check if the product is already added into favorite
    fun isProductFavorite(id:Long) {
        viewModelScope.launch {
            productDatabase.dao.getProduct(id).collect{
                val isFavorite = it != null && !it.isDeleted
                _viewState.value = _viewState.value.copy(
                    buttonText = if (isFavorite) ButtonText.RemoveFromFavorite.text else
                        ButtonText.AddToFavorite.text,
                    isFavorite = isFavorite
                )
            }
        }
    }

    fun changeFavoriteStatus(id:Long){
        isProductFavorite(id)
        if (_viewState.value.isFavorite){
            removeFromFavorite(id)
        }else{
            addToFavorite(id)
        }
    }

    private fun addToFavorite(id:Long) {
        viewModelScope.launch {
            productDatabase.dao.upsertProduct(ProductEntity(id,false))
            _viewState.value = _viewState.value.copy(
                buttonText = ButtonText.RemoveFromFavorite.text,
                isFavorite = true
            )

        }
    }

    private fun removeFromFavorite(id:Long) {
        viewModelScope.launch {
            productDatabase.dao.upsertProduct(ProductEntity(id,true))
            _viewState.value = _viewState.value.copy(
                buttonText = ButtonText.AddToFavorite.text,
                isFavorite = false
            )
        }
    }

    fun getProduct(id: Long) {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(isLoading = true)
            try {
                val res = apiSource.getProducts()
                if (res.isSuccessful) {
                    res.body()?.let {
                        val product = it.products.find { product: Product -> product.id == id }
                        if (product == null) {
                            _viewState.value = _viewState.value.copy(
                                isLoading = false,
                                error = false,
                                notFound = true
                            )
                        } else {
                            _viewState.value = _viewState.value.copy(
                                isLoading = false,
                                product = product,
                                error = false
                            )
                        }

                    }
                }
            } catch (error: Error) {
                Log.e(HOME_VM_LOG, "Error occur in homevm: ${error.message}")
                _viewState.value = _viewState.value.copy(error = true, isLoading = false)
            }
        }
    }
}