package com.santhoshkumar.storeapp.api

import com.santhoshkumar.storeapp.models.ProductData
import retrofit2.Call
import retrofit2.http.GET

// API Service which defines the endpoints and methods to fetch data
interface ApiService {
    @GET("/products")
    fun getProductData(): Call<List<ProductData>>
}