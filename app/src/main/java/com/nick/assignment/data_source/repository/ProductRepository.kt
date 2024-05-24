package com.nick.assignment.data_source.repository

import com.nick.assignment.data_source.api.ProductApiService
import com.nick.assignment.domain.Product
import com.nick.assignment.utils.ResponseState
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProductRepository(private val apiService: ProductApiService) {

    suspend fun fetchProducts(): List<Product> {
        return try {
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addProduct(
        productName: String,
        productType: String,
        price: String,
        tax: String
    ): ResponseState<Boolean> {
        val productNameBody = productName.toRequestBody(MultipartBody.FORM)
        val productTypeBody = productType.toRequestBody(MultipartBody.FORM)
        val priceBody = price.toRequestBody(MultipartBody.FORM)
        val taxBody = tax.toRequestBody(MultipartBody.FORM)



        return try {
            val response =
                apiService.addProduct(productNameBody, productTypeBody, priceBody, taxBody)

            if (response.isSuccessful) {
                ResponseState.Success(true)
            } else {
                ResponseState.Failure("Failed to add product")
            }
        } catch (e: Exception) {
            ResponseState.Failure(e.message ?: "Unknown error occurred")
        }
    }
}
