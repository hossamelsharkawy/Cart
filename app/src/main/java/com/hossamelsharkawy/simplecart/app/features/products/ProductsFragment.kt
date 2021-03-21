package com.hossamelsharkawy.simplecart.app.features.products

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hossamelsharkawy.base.ui.recycel.*
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val viewBinding by viewBinding(FragmentProductsBinding::bind)

    private val mViewModel: ProductViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        setProductsUi()
    }

    private fun setViewModel() = with(viewBinding) {
        withFragment(this)
        this.viewModel = this@ProductsFragment.mViewModel
    }

    private fun setProductsUi() = with(viewBinding) {
        val adapter = ProductsAdapter(object : ProductItemClickListener {
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


        productList.adapter = adapter
        viewModel?.items?.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }
}

fun Fragment.withFragment(dataBinding: ViewDataBinding) {
    dataBinding.lifecycleOwner = this.viewLifecycleOwner
}




