package com.santhoshkumar.storeapp.repositories

import com.santhoshkumar.storeapp.api.RetrofitInstance
import com.santhoshkumar.storeapp.models.ProductData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDataRepo {
//    REPOS ACTS AS A DATA PROVIDER (BASED ON DATA MODEL) FOR VIEWMODEL WHICH FETCHES LIVE DATA FROM API OR LOCAL DATABASE
//    GENERALLY API CALLS OR DATABASE CALLS ARE DONE HERE TO RECEIVE RESPONSE OR DATA RESPECTIVELY
//    ACT AS A RESERVOIR FOR DATA STORAGE

    fun getCatalogueData(onSuccess: (List<ProductData>) -> Unit, onFailure: (String) -> Unit) {
//      CALLING API SERVICE TO FETCH DATA
        RetrofitInstance.apiService.getProductData()
            .enqueue(object : Callback<List<ProductData>> {
                override fun onResponse(
                    call: Call<List<ProductData>>,
                    response: Response<List<ProductData>>
                ) {
                    if (response.isSuccessful) {
                        onSuccess(response.body()!!)
                    } else {
                        onFailure("Server is Not Responding")
                    }
                }

                override fun onFailure(call: Call<List<ProductData>>, t: Throwable) {
                    onFailure("Error Occured : ${t.message} ")
                }

            })
    }
}