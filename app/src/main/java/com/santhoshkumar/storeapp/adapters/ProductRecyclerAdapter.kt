package com.santhoshkumar.storeapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.santhoshkumar.storeapp.databinding.CardLayoutBinding
import com.santhoshkumar.storeapp.models.ProductData
import com.santhoshkumar.storeapp.views.activity.ProductViewControllerActivity

// Adapter Class for Recycler View of Products
class ProductRecyclerAdapter :
    RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder>() {

    // View Holder Class which Holds the dynamic data with the Specified Layout
    inner class ProductViewHolder(val itemBinding: CardLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    //    DifferUtil used for listening changes made in the Adapter Data by
//    Checking items / contents are same if it is then it will update the Adapter
    private val differCallback = object : DiffUtil.ItemCallback<ProductData>() {
        override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    // Creating ViewHolder with Specified Layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            CardLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Returning length of the data passed on Adapter
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    //  Binding Data With the Specified ViewHolder
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = differ.currentList[position]

        holder.itemBinding.apply {
            Glide.with(holder.itemView)
                .load(product.image)
                .into(productImg)

            productTitle.text = product.title
            productDesc.text = product.description
            priceText.text = "$${product.price}"

            root.setOnClickListener {
                val context = holder.itemBinding.root.context

                // Passing Serializable Data with the type of ProductData Model
                val intent = Intent(context, ProductViewControllerActivity::class.java).apply {
                    putExtra("product_data", product)
                }
                context.startActivity(intent)
            }
        }


    }


}