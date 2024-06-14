package com.santhoshkumar.storeapp.views.activity

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.santhoshkumar.storeapp.R
import com.santhoshkumar.storeapp.adapters.ProductRecyclerAdapter
import com.santhoshkumar.storeapp.databinding.ActivityStoreMainBinding
import com.santhoshkumar.storeapp.models.ProductData
import com.santhoshkumar.storeapp.repositories.ProductDataRepo
import com.santhoshkumar.storeapp.utils.CommonUtils
import com.santhoshkumar.storeapp.viewmodels.ProductDataViewModel
import com.santhoshkumar.storeapp.viewmodels.ProductViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Objects

class StoreMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreMainBinding
    private lateinit var productAdapter: ProductRecyclerAdapter
    private lateinit var productViewModel: ProductDataViewModel
    private lateinit var productList: List<ProductData>
    private lateinit var filteredCategoryList: List<ProductData>
    private var isLoading = false
    private val selectedCategories = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // INSTALLING SPLASH SCREEN BEFORE CONTENT VIEW IS SET
        installSplashScreen()
        binding = ActivityStoreMainBinding.inflate(layoutInflater)
        //  VIEW BINDING BINDS THE LAYOUT WITH THE ACTIVITY
        //  WHICH REDUCES THE BOILERPLATE CODE
        setContentView(binding.root)

        productAdapter = ProductRecyclerAdapter()
        productList = emptyList()
        filteredCategoryList = emptyList()

        setUpCategoryButtons()

        //  INITIALIZING VIEWMODEL
        val productRepo = ProductDataRepo()
        val viewModelProvider = ProductViewModelProvider(application, productRepo)
        productViewModel =
            ViewModelProvider(this, viewModelProvider)[ProductDataViewModel::class.java]

        //  CHANGING SEARCH FIELD FONT FAMILY AND COLOR OF TEXT
        val customFont = ResourcesCompat.getFont(this@StoreMainActivity, R.font.inter_medium)
        val searchEditText: EditText =
            binding.searchField.findViewById(androidx.appcompat.R.id.search_src_text)
        searchEditText.setTypeface(customFont)
        searchEditText.setTextColor(resources.getColor(R.color.primaryColor, null))

        binding.searchField.clearFocus()

        productViewModel.getProductData()

//        OBSERVING VIEWMODEL FOR ANY DATA RECEIVED THROUGH API SERVICE
//        AND UPDATE UI
        productViewModel.isLoading.observe(this) { loading ->
            isLoading = loading
            if (isLoading) {
                CommonUtils.showLoadingDialog(this@StoreMainActivity)
            } else {
                CommonUtils.dismissLoadingDialog()
                isLoading = false
            }
        }

        productViewModel.message.observe(this) { message ->
            showRetryDialog()
            CommonUtils.makeToast(this@StoreMainActivity, message)
        }

        productViewModel.productLiveData.observe(this) { productData ->
            productList = productData
            filteredCategoryList = productList
            productAdapter.differ.submitList(productList)

            //  SHOWING LOADER UNTIL THE DATA IS FETCH AND POPULATE THE RECYCLER
            if (isLoading || productList.isNotEmpty()) {
                CommonUtils.dismissLoadingDialog()
                isLoading = false
            }
        }

        binding.itemRecycler.apply {
            layoutManager = LinearLayoutManager(this@StoreMainActivity)
            adapter = productAdapter
        }


        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.searchField.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    // FILTERING THE RECYCLER DATA AS PER SEARCH TEXT
                    // USING SEPARATE THREAD TO AVOID UI BLOCKING
                    CoroutineScope(Dispatchers.Main).launch {
                        filter(newText)
                    }
                    return true
                } else {
                    productAdapter.differ.submitList(filteredCategoryList)

                    return true
                }
            }

        })
    }

    private fun filter(text: String) {
        val filteredList = filteredCategoryList.filter { product ->
            product.title.contains(text.trim(), ignoreCase = true)
        }
        // SUBMITTING UPDATED LIST AS PER THE FILTER
        // TO RECYCLER VIEW FOR UPDATING UI BY CALLING DIFFERUTIL CALLBACK
        productAdapter.differ.submitList(filteredList)
    }

    private fun filterProducts() {
        // FILTERING PRODUCTS LIST AS PER THE SELECTED CATEGORY
        // AND UPDATING RECYCLER DATA
        filteredCategoryList = productList.filter { product ->
            selectedCategories.contains(product.category)
        }
        if(selectedCategories.isEmpty()){
            binding.itemRecycler.visibility = View.GONE
            binding.noneSelectedText.visibility = View.VISIBLE
        }else{
            binding.itemRecycler.visibility = View.VISIBLE
            binding.noneSelectedText.visibility = View.GONE
        }
        productAdapter.differ.submitList(filteredCategoryList)
    }


    private fun setUpCategoryButtons() {
        // MAPPING CATEGORY CARD BUTTONS WITH
        // CATEGORY DATA RECEIVED FROM API SERVICE
        val categories = mapOf(
            binding.mensBtn to "men's clothing",
            binding.womensBtn to "women's clothing",
            binding.electronicsBtn to "electronics",
            binding.jeweleryBtn to "jewelery"
        )

        categories.forEach { (button, category) ->
            selectCategory(button)

            // UPDATING THE UI BASED ON SELECTING AND UNSELECTING THE CATEGORY CARD
            button.setOnClickListener {
                if (selectedCategories.contains(category)) {
                    deselectCategory(button)
                    selectedCategories.remove(category)
                } else {
                    selectCategory(button)
                    selectedCategories.add(category)
                }
                filterProducts()
            }
        }

        selectedCategories.addAll(categories.values)
        filterProducts()
    }

    private fun selectCategory(cardView: CardView) {
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.primaryVariant))
        val imageView = cardView.getChildAt(0) as ImageView
        imageView.setColorFilter(
            ContextCompat.getColor(this, R.color.white),
            PorterDuff.Mode.SRC_IN
        )
    }

    private fun deselectCategory(cardView: CardView) {
        cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        val imageView = cardView.getChildAt(0) as ImageView
        imageView.setColorFilter(
            ContextCompat.getColor(this, R.color.primaryVariant),
            PorterDuff.Mode.SRC_IN
        )
    }

//    SHOWING ALERT DIALOG WHEN ERROR OCCURS WHILE FETCHING DATA
    private fun showRetryDialog() {
        val view =
            LayoutInflater.from(this).inflate(R.layout.alert_dialog_layout, null)

        val headerTextView = view.findViewById<TextView>(R.id.alertHeader)
        val messageTextView = view.findViewById<TextView>(R.id.alertMsg)
        val positiveButton = view.findViewById<Button>(R.id.positiveBtn)

        headerTextView.text = "Error"
        messageTextView.text = "Failed to fetch data. Would you like to try again?"
        positiveButton.text = "Retry"

        val builder = AlertDialog.Builder(this)
        builder.setView(view)

        val dialog = builder.create()
        dialog.setCancelable(false)
        Objects.requireNonNull<Window>(dialog.window)
            .setBackgroundDrawable(
                ColorDrawable(
                    Color.TRANSPARENT
                )
            )

        dialog.show()

        positiveButton.setOnClickListener {
            dialog.dismiss()
            productViewModel.getProductData()
        }
    }
}