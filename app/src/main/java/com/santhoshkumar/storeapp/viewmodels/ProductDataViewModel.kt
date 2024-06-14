package com.santhoshkumar.storeapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.santhoshkumar.storeapp.models.ProductData
import com.santhoshkumar.storeapp.repositories.ProductDataRepo
import kotlinx.coroutines.launch

class ProductDataViewModel(app: Application, private val productRepo: ProductDataRepo) :
    AndroidViewModel(app) {


//  VIEW MODEL ACT AS BRIDE BETWEEN VIEW AND REPOSITORY (DATA)

    //  INITIALIZING VARIABLES TO STORE THE LIVE DATA

    val productLiveData = MutableLiveData<List<ProductData>>()
    val message = MutableLiveData<String>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _isLoading.value = false
    }

    //    VIEW MODEL SCOPE ACTS AS COROUTINE SCOPE CONTROLLER TO RUN AS
    //    SEPARATE THREAD TO AVOID MAIN THREAD BLOCKING

    fun getProductData() = viewModelScope.launch {
        _isLoading.value = true
        productRepo.getCatalogueData(
            onSuccess = {
                //  POST VALUE METHOD IS USED TO STORE THE RESPONSE AS LIVE DATA
                productLiveData.postValue(it)
                _isLoading.value = false
            },
            onFailure = {
                _isLoading.value = false
                message.postValue(it)
            }
        )
    }

}