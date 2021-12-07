package com.hossamelsharkawy.simplecart.app.features.cart

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hossamelsharkawy.simplecart.app.features.products.ListAdapter2
import com.hossamelsharkawy.simplecart.app.features.products.ProductDiffCallback
import com.hossamelsharkawy.simplecart.app.features.products.ProductItemClickListener
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.databinding.CartItemItemBinding

class CartItemsAdapter(private val itemClickCallback: ProductItemClickListener) :
    ListAdapter2<Product, CartItemsAdapter.ViewHolder>(ProductDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            CartItemItemBinding.inflate(
                getLayoutInflater(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(
        private val binding: CartItemItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            item = product
            itemClick = itemClickCallback
            executePendingBindings()
        }
    }

}