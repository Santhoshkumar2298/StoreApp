package com.santhoshkumar.storeapp.models

import java.io.Serializable

data class Rating(
    val count: Int,
    val rate: Double
): Serializable