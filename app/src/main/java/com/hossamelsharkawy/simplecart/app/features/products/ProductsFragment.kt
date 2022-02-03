package com.hossamelsharkawy.simplecart.app.features.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ListAdapter
import com.hossamelsharkawy.base.extension.collect
import com.hossamelsharkawy.base.extension.navigate
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.app.Route
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products
import dagger.hilt.android.AndroidEntryPoint

import androidx.fragment.app.activityViewModels

@AndroidEntryPoint
class ProductsFragment : Fragment() {


    val viewModel: ProductViewModel by viewModels()


    val navCartAction by lazy { { navigate(R.id.action_ProductsFragment_to_bottomSheet) } }
    val navNotificationAction by lazy { { navigate(R.id.action_ProductsFragment_to_notification) } }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
              //  MainView()
            }
        }
    }

    @Composable
    fun MainView() {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.primary,
                    title = { Text(stringResource(R.string.app_name)) },

                    )
            }
        ) {

        }
    }


    private fun subUIEvents() = with(viewModel) {

        collect(itemsFlow) {
            adapter?.submitList(it)
        }

        collect(navActionFlow) {
            when (it) {
                Route.Notification -> navNotificationAction.invoke()
                Route.CartItems -> navCartAction.invoke()


            }
        }
    }


    var adapter: ListAdapter<Product, *>? = null

    private fun setProductsUi() {
        adapter = ProductsAdapter(object : ProductItemClickListener {
            override fun onAddToCart(product: Product) {
                viewModel.addToCart(product)
            }

            override fun onPlusQty(product: Product) {
                viewModel.onPlusQty(product)
            }

            override fun onMinQty(product: Product) {
                viewModel.onMinQty(product)
            }
        })

        //viewBinding.productList.adapter = adapter
    }
}

fun Fragment.withFragment(dataBinding: ViewDataBinding) {
    dataBinding.lifecycleOwner = this.viewLifecycleOwner
}






