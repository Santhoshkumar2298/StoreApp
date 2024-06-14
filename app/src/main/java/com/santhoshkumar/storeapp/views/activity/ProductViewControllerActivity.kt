package com.santhoshkumar.storeapp.views.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.santhoshkumar.storeapp.R
import com.santhoshkumar.storeapp.databinding.ActivityProductViewControllerBinding
import com.santhoshkumar.storeapp.models.ProductData
import com.santhoshkumar.storeapp.utils.CommonUtils

class ProductViewControllerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductViewControllerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        VIEW BINDING BINDS THE LAYOUT WITH THE ACTIVITY
//        WHICH REDUCES THE BOILERPLATE CODE
        binding = ActivityProductViewControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productData = intent.getSerializableExtra("product_data") as? ProductData
        binding.detailsHolderLayout.visibility = View.GONE

        if (productData != null) {
            Glide.with(this@ProductViewControllerActivity)
                .load(productData.image)
                .into(binding.productImg)

            binding.productTitle.text = productData.title
            binding.productDesc.text = productData.description
            binding.priceText.text = "$${productData.price}"
            binding.category.text = productData.category
            binding.availability.text = "${productData.rating.count} Nos."
            binding.ratingsText.text = "${productData.rating.rate} / 5.0"

        } else {
            CommonUtils.makeToast(this, "NO DATA RECEIVED")
        }

        showDetails()

        binding.detailsCard.setOnClickListener {
            showDetails()
        }

        binding.reviewCard.setOnClickListener {
            showReviews()
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

    }

    private fun showDetails() {
        //    CHANGING THE VIEW OF THE CARD WHEN IT IS SELECTED && UNSELECTED
        binding.detailsCard.setCardBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.primaryVariant
            )
        )
        binding.detailsText.setTextColor(ContextCompat.getColor(this, R.color.white))
        binding.reviewCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.reviewText.setTextColor(ContextCompat.getColor(this, R.color.grey))

        //    UPDATING UI BASED ON SELECTION
        binding.detailsHolderLayout.visibility = View.VISIBLE
        binding.reviewHolderLayout.visibility = View.GONE
    }

    private fun showReviews() {
        binding.reviewCard.setCardBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.primaryVariant
            )
        )
        binding.reviewText.setTextColor(ContextCompat.getColor(this, R.color.white))
        binding.detailsCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.detailsText.setTextColor(ContextCompat.getColor(this, R.color.grey))

        binding.detailsHolderLayout.visibility = View.GONE
        binding.reviewHolderLayout.visibility = View.VISIBLE
    }
}