package com.nick.assignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajit.swipeandroidassignment.R
import com.nick.assignment.domain.Product
import com.ajit.swipeandroidassignment.databinding.ItemProductBinding
import com.bumptech.glide.Glide

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    private val productList: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = productList[position]
        holder.bind(item)
    }

    fun updateList(products: List<Product>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(product: Product) {
            binding.textViewProductName.text = "Product Name: ${product.product_name}"
            binding.textViewProductType.text = "Product Type: ${product.product_type}"
            binding.textViewPrice.text = "Price: ${product.price}"
            binding.textViewTax.text = "Tax: ${product.tax}%"
            Glide.with(binding.imageViewProduct)
                .load(product.image)
                .placeholder(R.drawable.images)
                .into(binding.imageViewProduct)


        }
    }
}