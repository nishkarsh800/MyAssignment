package com.nick.assignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nick.assignment.domain.Product
import com.nick.assignment.data_source.repository.ProductRepository
import com.nick.assignment.utils.ResponseState
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productListState = MutableLiveData<ResponseState<List<Product>>>()
    val productListState: LiveData<ResponseState<List<Product>>> get() = _productListState

    private val _addProductResult = MutableLiveData<ResponseState<Boolean>>()
    val addProductResult: LiveData<ResponseState<Boolean>> get() = _addProductResult

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _productListState.value = ResponseState.Loading

            try {
                val products = repository.fetchProducts()
                _productListState.value = ResponseState.Success(products)
            } catch (e: Exception) {
                _productListState.value = ResponseState.Failure(e.message)
            }
        }
    }




    fun addProduct(product: Product) {
        viewModelScope.launch {
            _addProductResult.value = ResponseState.Loading

            try {
                val success = repository.addProduct(product.product_name,product.product_type,product.tax.toString(),product.price.toString())
                _addProductResult.value = ResponseState.Success(success != null)
            } catch (e: Exception) {
                _addProductResult.value = ResponseState.Failure(e.message)
            }
        }
    }
}
