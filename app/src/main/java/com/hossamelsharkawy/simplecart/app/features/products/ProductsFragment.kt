package com.hossamelsharkawy.simplecart.app.features.products

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ListAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hossamelsharkawy.base.extension.collect
import com.hossamelsharkawy.base.extension.navigate
import com.hossamelsharkawy.base.ui.recycel.*
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.app.Route
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val viewBinding by viewBinding(FragmentProductsBinding::bind)
    private lateinit var mViewModel: ProductViewModel

    val navCartAction by lazy { { navigate(R.id.action_ProductsFragment_to_bottomSheet) } }
    val navNotificationAction by lazy { { navigate(R.id.action_ProductsFragment_to_notification) } }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        setProductsUi()
        subUIEvents()
    }


    private fun subUIEvents() = with(mViewModel) {

        collect(itemsFlow) {
            adapter?.submitList(it)
        }

        collect(navActionFlow) {
            when (it) {
                Route.Notification -> navNotificationAction.invoke()
                Route.CartItems -> navCartAction.invoke()

                Route.ProductInfo -> TODO()
                Route.ProductsList -> TODO()
            }
        }
    }


    private fun setViewModel() = with(viewBinding) {
        mViewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        withFragment(this)
        this.viewModel = this@ProductsFragment.mViewModel
    }


    var adapter: ListAdapter<Product, *>? = null

    private fun setProductsUi() {
        adapter = ProductsAdapter(object : ProductItemClickListener {
            override fun onAddToCart(product: Product) {
                mViewModel.addToCart(product)
            }

            override fun onPlusQty(product: Product) {
                mViewModel.onPlusQty(product)
            }

            override fun onMinQty(product: Product) {
                mViewModel.onMinQty(product)
            }
        })

        viewBinding.productList.adapter = adapter
    }
}

fun Fragment.withFragment(dataBinding: ViewDataBinding) {
    dataBinding.lifecycleOwner = this.viewLifecycleOwner
}




