package com.hossamelsharkawy.simplecart.app.features.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import com.hossamelsharkawy.simplecart.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject internal constructor(
    private val productsRepository: IProductsRepository,
    private val cartRepository: ICartRepository
) : ViewModel() {


    private val _items = MutableLiveData<Products>().apply { value = arrayListOf() }
    var items: LiveData<Products> = _items

    private val _cartItems = MutableLiveData<Products>()
    var cartItems: LiveData<Products> = _cartItems


    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading


    init {
        fetchProducts()
    }

    private fun fetchProducts() = viewModelScope.launch {
        _dataLoading.value = true
        val product = showAllProducts(productsRepository, cartRepository)

        if (product.isNullOrEmpty()) {
            _items.value = arrayListOf()
        } else {
            _items.value = ArrayList(product)
        }
        _dataLoading.value = false
    }

     fun fetchCartItems() = viewModelScope.launch {
        val product = showAllCartItems(productsRepository, cartRepository)

        if (product.isNullOrEmpty()) {
            _cartItems.value = arrayListOf()
        } else {
            _cartItems.value = ArrayList(product)
        }
    }


    fun addToCart(product: Product) = viewModelScope.launch {
        product.addToCart(cartRepository)
    }

    fun onPlusQty(product: Product) = viewModelScope.launch {
        product.plusQtyInCart(cartRepository)
    }

    fun onMinQty(product: Product) = viewModelScope.launch {
        product.minQtyInCart(cartRepository)

    }
}


