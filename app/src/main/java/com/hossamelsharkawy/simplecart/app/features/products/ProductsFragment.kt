package com.hossamelsharkawy.simplecart.app.features.products

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hossamelsharkawy.base.ui.recycel.*
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.databinding.AppBarHomeBinding.bind
import com.hossamelsharkawy.simplecart.databinding.CartBarViewBinding
import com.hossamelsharkawy.simplecart.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val viewBinding by viewBinding(FragmentProductsBinding::bind)

    private lateinit var mViewModel: ProductViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        setProductsUi()
    }

    private fun setViewModel() = with(viewBinding) {
        mViewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
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


        lifecycleScope.launch {
            viewModel?.items?.collect {result ->
                adapter.submitList(result)
            }
        }

        viewModel?.itemsCount?.observe(viewLifecycleOwner) { result ->
            viewBinding.appBarHome.cartBarView.txtCartCountCount.text = (result ?: 0).toString()
        }

       viewBinding.appBarHome.cartBarView.imgCartCount.setOnClickListener {
           findNavController().navigate(R.id.action_ProductsFragment_to_bottomSheet)
       }


    }
}

fun Fragment.withFragment(dataBinding: ViewDataBinding) {
    dataBinding.lifecycleOwner = this.viewLifecycleOwner
}




