package com.santhoshkumar.storeapp.models

import java.io.Serializable

// Data Class defines the Data Structure
// Serializable used for passing the data as a package (same type)
data class ProductData(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
) : Serializable