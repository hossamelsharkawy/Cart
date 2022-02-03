package com.hossamelsharkawy.simplecart.app.features.products


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.databinding.ProductItemBinding


interface ProductItemClickListener {
    fun onAddToCart(product: Product) {}
    fun onPlusQty(product: Product)
    fun onMinQty(product: Product)
    fun view(product: Product) {}
}

class ProductsAdapter(private val itemClickCallback: ProductItemClickListener) :
    ListAdapter2<Product, ProductsAdapter.ViewHolder>(ProductDiffCallback) {

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

}

/**************************************************************************/



object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
}


abstract class ListAdapter2<T, V : RecyclerView.ViewHolder>(
    mDiffer: DiffUtil.ItemCallback<T>,
) :
    ListAdapter<T, V>(mDiffer) {

    private var layoutInflater: LayoutInflater? = null

    fun getLayoutInflater(context: Context): LayoutInflater {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context)
        }
        return layoutInflater!!
    }

    fun getLayoutInflater(viewGroup: ViewGroup) = getLayoutInflater(viewGroup.context)


}

