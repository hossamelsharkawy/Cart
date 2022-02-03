package com.hossamelsharkawy.simplecart.app.features.products

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.hossamelsharkawy.base.extension.launch
import com.hossamelsharkawy.base.extension.shareInShort
import com.hossamelsharkawy.base.extension.vmStateShort
import com.hossamelsharkawy.simplecart.app.UIRouter
import com.hossamelsharkawy.simplecart.app.features.swapList
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import com.hossamelsharkawy.simplecart.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProductViewModel1 @Inject internal constructor(
    private val productsRepository: IProductsRepository,
    private val cartRepository: ICartRepository,
    val uIRoute: UIRouter
) : ViewModel() {

    val dataLoading = true.vmStateShort(this)

    var itemsState = mutableStateListOf<Product>()
        private set

    var cartItemsState = mutableStateListOf<Product>()
        private set

    var itemsCount = mutableStateOf(0)
        private set




    fun openCart() {
        launch { uIRoute.navToCartItems() }
    }

    fun openNotification() {
        launch { uIRoute.navToNotification() }
    }

    fun openProductInfo(product: Product) {
        launch { uIRoute.navToProductInfo(product) }
    }

    init {
        fetchProducts()
    }

    private fun fetchProducts() = launch {
        dataLoading.emit(true)
        showAllProducts(productsRepository, cartRepository)
            .also { itemsState.swapList(it) }
            .also { dataLoading.value = false }
            .also { fetchCartItems() }
    }

    private fun fetchCartItems() = launch {
        showAllCartItems(productsRepository, cartRepository)
            .also { cartItemsState.swapList(it) }
            .let { itemsCount.value = it.sumOf { cartItem -> cartItem.qtyInCart } }
    }

    fun addToCart(product: Product) = launch {
        cartRepository.addNewCartItem(product)
            .also { fetchCartItems() }
            .let { fetchProducts() }
    }

    fun plusQty(product: Product) = launch {
        product
            .plusQtyInCart(cartRepository)
            .let { fetchCartItems() }
            .let { fetchProducts() }
    }


    fun minQty(product: Product) = launch {
        product
            .minQtyInCart(cartRepository)
            .let { fetchCartItems() }
            .let { fetchProducts() }

    }

    fun clearCart() = launch {
        clearCart(cartRepository)
            .also { fetchCartItems() }
            .let { fetchProducts() }

    }
}







