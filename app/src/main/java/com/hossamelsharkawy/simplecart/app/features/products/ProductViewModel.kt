package com.hossamelsharkawy.simplecart.app.features.products

import androidx.lifecycle.ViewModel
import com.hossamelsharkawy.base.extension.launch
import com.hossamelsharkawy.base.extension.shareInShort
import com.hossamelsharkawy.base.extension.vmStateShort
import com.hossamelsharkawy.simplecart.app.UIRouter
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import com.hossamelsharkawy.simplecart.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject internal constructor(
    private val productsRepository: IProductsRepository,
    private val cartRepository: ICartRepository,
    private val uIRoute: UIRouter
) : ViewModel() {


    val itemsCount = 0.vmStateShort(this)
    val dataLoading = true.vmStateShort(this)
    val itemsFlow = listOf<Product>()
        .vmStateShort(this)

    val cartItemsFlow = listOf<Product>().vmStateShort(this)
    val navActionFlow = uIRoute.navAction.shareInShort(this)



    fun openCart() {
        launch { uIRoute.navToCartItems() }
    }

    fun openNotification() {
        launch { uIRoute.navToNotification() }
    }


    init {
        fetchProducts()
    }

    private fun fetchProducts() = launch {
        dataLoading.emit(true)

        showAllProducts(productsRepository, cartRepository)
            .also { itemsFlow.emit(it) }
            .also { dataLoading.value = false }
            .also { fetchCartItems() }
    }


    private fun fetchCartItems() = launch {
        showAllCartItems(productsRepository, cartRepository)
            .also { cartItemsFlow.emit(it) }
            .let { itemsCount.emit(it.sumOf { cartItem -> cartItem.qtyInCart }) }
    }

    fun addToCart(product: Product) = launch {
        cartRepository.addNewCartItem(product)
        fetchCartItems()
    }

    fun onPlusQty(product: Product) = launch {
        product
            .plusQtyInCart(cartRepository)
            .let { fetchCartItems() }
    }

    fun onMinQty(product: Product) = launch {
        product
            .minQtyInCart(cartRepository)
            .let { fetchCartItems() }
    }
}







