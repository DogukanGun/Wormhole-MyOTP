package com.dag.check24di.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dag.check24di.dao.ProductDatabase
import com.dag.check24di.data.Product
import com.dag.check24di.retrofit.ApiSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeVM constructor(
    private val apiSource: ApiSource,
    private val productDatabase: ProductDatabase
) : ViewModel() {

    private var _viewState: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val viewState: StateFlow<HomeScreenState> = _viewState.asStateFlow()
    private var refreshAttemptCount = 0

    //get favorite products
    private fun getFavoriteProducts(){
        viewModelScope.launch {
            productDatabase.dao.getAllRecords().collect{
                _viewState.value = _viewState.value.copy(favoriteProducts = it)
            }
        }
    }

    //get products from api
    private fun getProductsFromApi(){
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(isLoading = true, products = emptyList())
            if (refreshAttemptCount <= 3) {
                try {
                    val res = apiSource.getProducts()
                    if (res.isSuccessful) {
                        res.body()?.let {
                            refreshAttemptCount++
                            _viewState.value = _viewState.value.copy(
                                isLoading = false,
                                products = it.products,
                                header = it.header,
                                filters = it.filters,
                                error = false
                            )
                        }
                    }
                } catch (error: Error) {
                    Log.e(HOME_VM_LOG, "Error occur in homevm: ${error.message}")
                    _viewState.value = _viewState.value.copy(error = true, isLoading = false)
                }
            } else {
                refreshAttemptCount = 0
                _viewState.value = _viewState.value.copy(isLoading = false, error = true)

            }
        }

    }

    fun clearCount(){
        refreshAttemptCount = 0
    }

    fun getProducts() {
        getFavoriteProducts()
        getProductsFromApi()
    }

    companion object {
        const val HOME_VM_LOG = "HOME_VM_LOG"
    }
}