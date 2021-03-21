package com.hossamelsharkawy.simplecart.app.features.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hossamelsharkawy.simplecart.app.features.products.ProductDiffCallback
import com.hossamelsharkawy.simplecart.app.features.products.ProductItemClickListener
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.databinding.ProductItemBinding

class CartItemsAdapter(private val itemClickCallback: ProductItemClickListener) :
    ListAdapter<Product, CartItemsAdapter.ViewHolder>(ProductDiffCallback()) {

    private var layoutInflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ProductItemBinding.inflate(
                getLayoutInflater(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(
        private val binding: ProductItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            item = product
            itemClick = itemClickCallback
            executePendingBindings()
        }
    }


    private fun getLayoutInflater(context: Context): LayoutInflater {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context)
        }
        return layoutInflater!!
    }
}