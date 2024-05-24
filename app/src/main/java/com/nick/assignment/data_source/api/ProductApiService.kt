package com.nick.assignment.data_source.api

import com.nick.assignment.domain.Product
import retrofit2.Response
import retrofit2.http.*

interface ProductApiService {

    @GET("get")
    suspend fun getProducts(): Response<List<Product>>



    @Multipart
    @POST("add")
    suspend fun addProduct(
        @Part("product_name") productName: okhttp3.RequestBody,
        @Part("product_type") productType: okhttp3.RequestBody,
        @Part("price") price: okhttp3.RequestBody,
        @Part("tax") tax: okhttp3.RequestBody
    ): Response<Product>
}
