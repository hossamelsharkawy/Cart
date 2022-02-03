package com.hossamelsharkawy.simplecart.app.features.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hossamelsharkawy.base.extension.launch
import com.hossamelsharkawy.base.extension.shareInShort
import com.hossamelsharkawy.base.extension.vmStateShort
import com.hossamelsharkawy.simplecart.app.UIRouter
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import com.hossamelsharkawy.simplecart.domain.usecases.minQtyInCart
import com.hossamelsharkawy.simplecart.domain.usecases.plusQtyInCart
import com.hossamelsharkawy.simplecart.domain.usecases.showAllCartItems
import com.hossamelsharkawy.simplecart.domain.usecases.showAllProducts
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


    var itemsState by mutableStateOf(listOf<Product>())
        private set



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
            .also {
             //   itemsState = arrayListOf()
                itemsState =it
              //  itemsFlow.value = ArrayList(it)
            }
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
            .also { fetchCartItems() }
            .let { fetchProducts() }
    }

    fun onPlusQty(product: Product) = launch {
        product
            .plusQtyInCart(cartRepository)
            .let { fetchCartItems() }
            .let { fetchProducts() }

    }

    fun onMinQty(product: Product) = launch {
        product
            .minQtyInCart(cartRepository)
            .let { fetchCartItems() }
            .let { fetchProducts() }

    }
}







