package com.hossamelsharkawy.simplecart.app.features.products

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.hossamelsharkawy.base.extension.launch
import com.hossamelsharkawy.base.extension.vmStateShort
import com.hossamelsharkawy.simplecart.app.UIRouter
import com.hossamelsharkawy.simplecart.app.features.cart.CartStates
import com.hossamelsharkawy.simplecart.app.features.products.ProductsMapper.toGridItems
import com.hossamelsharkawy.simplecart.app.features.products.ProductsMapper.toMapByCategory
import com.hossamelsharkawy.simplecart.app.util.util.refresh
import com.hossamelsharkawy.simplecart.app.util.util.swapList
import com.hossamelsharkawy.simplecart.data.entities.Category
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
    val uIRoute: UIRouter
) : ViewModel() {

    val dataLoading = true.vmStateShort(this)

    var itemsState = mutableStateListOf<Product>()
        private set

    var itemsByCategoryState = mutableStateMapOf<Category, SnapshotStateList<Product>>()
        private set

    var itemsByCategoryStateFlat = mutableStateListOf<GridItem<*>>()
        private set

    var cart by mutableStateOf(CartStates)
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
            .also { itemsByCategoryState.swapList(it.toMapByCategory()) }
            .also { itemsByCategoryStateFlat.swapList(it.toGridItems(3)) }
            .also { dataLoading.value = false }
            .also { fetchCartItems() }
    }

    private fun fetchCartItems() = launch {
        showAllCartItems(productsRepository, cartRepository)
            .also { cart.cartItemsState.swapList(it) }
            .also {
                cart.itemsCountState.value =
                    it.sumOf { cartItem -> cartItem.qtyInCart }
                        .let { itemCount -> if (itemCount >= 99) "+99" else " $itemCount" }
            }
            .also {
                cart.subPriceState.value = it.sumOf { cartItem -> cartItem.cartPrice.toDouble() }
            }
            .also { cart.deliveryFeeState.value = 10.0 }
            .let {
                cart.totalPriceState.value = cart.subPriceState.value + cart.deliveryFeeState.value
            }
    }


    private fun Product.update() {
        itemsByCategoryState[category]?.refresh()
        itemsByCategoryStateFlat.refresh()
    }

    fun addToCart(product: Product) = launch {
        product
            .also { it.addToCart(cartRepository) }
            .also { it.update() }
            .let { fetchCartItems() }
    }

    fun plusQty(product: Product) = launch {
        product
            .also { it.plusQtyInCart(cartRepository) }
            .also { it.update() }
            .let { fetchCartItems() }
    }

    fun setQty(qty: Int, product: Product) = launch {
        product
            .also { it.selectQtyInCart(qty, cartRepository) }
            .also { it.update() }
            .let { fetchCartItems() }
    }


    fun minQty(product: Product) = launch {
        product
            .also { it.minQtyInCart(cartRepository) }
            .also { it.update() }
            .let { fetchCartItems() }

    }

    fun clearCart() = launch {
        clearCart(cartRepository)
            .also { fetchCartItems() }
            .let { fetchProducts() }
    }
}

