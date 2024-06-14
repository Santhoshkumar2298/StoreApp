package com.santhoshkumar.storeapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.santhoshkumar.storeapp.repositories.ProductDataRepo

class ProductViewModelProvider(
    private val app: Application,
    private val productRepo: ProductDataRepo
) : ViewModelProvider.Factory {
    //   VIEW MODEL PROVIDER FACTORY IS USED TO CREATE AN INSTANCE
    //   OF VIEWMODEL WITH DEPENDENCY INJECTION OF REPOSITORY
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDataViewModel(app, productRepo) as T
    }

}