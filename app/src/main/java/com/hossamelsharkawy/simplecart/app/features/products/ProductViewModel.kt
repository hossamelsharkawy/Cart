package com.hossamelsharkawy.simplecart.app.features.products

import androidx.lifecycle.*
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.data.entities.Resource
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import com.hossamelsharkawy.simplecart.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject internal constructor(
    private val productsRepository: IProductsRepository,
    private val cartRepository: ICartRepository
) : ViewModel() {

    private var _itemsCount = MutableStateFlow(0)
    var itemsCount = _itemsCount.asLiveData()


    private var _dataLoading = MutableStateFlow(true)
    var dataLoading = _dataLoading.asLiveData()


    var items = MutableStateFlow(arrayListOf<Product>())
        private set

    var cartItems = MutableStateFlow(arrayListOf<Product>())
        private set

    init {
        fetchProducts()
    }

    private fun fetchProducts() = viewModelScope.launch {
        _dataLoading.value = true
        val product = showAllProducts(productsRepository, cartRepository)

        if (product.isNullOrEmpty()) {
            items.value = arrayListOf()
        } else {
            items.value = ArrayList(product)
        }

        _dataLoading.value = false
        fetchCartItems()
    }

    private fun fetchCartItems() = viewModelScope.launch {
        val product = showAllCartItems(productsRepository, cartRepository)

        if (product.isNullOrEmpty()) {
            cartItems.value = arrayListOf()
        } else {
            cartItems.value = ArrayList(product)
        }
        _itemsCount.value = cartItems.value.sumBy { it.qtyInCart }
    }

    fun addToCart(product: Product) = viewModelScope.launch {
        product.addToCart(cartRepository)
        fetchCartItems()
    }

    fun onPlusQty(product: Product) = viewModelScope.launch {
        product.plusQtyInCart(cartRepository)
        fetchCartItems()
    }

    fun onMinQty(product: Product) = viewModelScope.launch {
        product.minQtyInCart(cartRepository)
        fetchCartItems()
    }
}


