package com.hossamelsharkawy.simplecart.data.cart

import android.util.SparseArray
import com.hossamelsharkawy.simplecart.data.entities.CartItem
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import java.util.*
import javax.inject.Inject

class CartDataSource @Inject constructor(
) : ICartRepository {

    var cartItems: SparseArray<CartItem>? = null
        private set

    override suspend fun getCartItems() = cartItems?.asList()
    override suspend fun addToCart(product: Product) = product.addToCart()
    override suspend fun plusQtyInCart(product: Product) = product.addToCart()
    override suspend fun minQtyInCart(product: Product) = product.minQtyOrRemove()


    private fun Product.addToCart() =
        fromCartItem()
            ?.also { it.qty = qtyInCart }
            ?: toCartItem()

    private fun Product.minQtyOrRemove(): CartItem? {
        return fromCartItem()
            ?.apply { this.qty = qtyInCart }
            ?.takeIf { it.qty > 0 }
            ?: remove()
    }

    private fun Product.remove(): Nothing? {
        getItemCartOrInit().remove(id)
        return null
    }


    private fun Product.toCartItem() =
        CartItem(id, qtyInCart)
            .apply { getItemCartOrInit().put(id, this) }

    private fun Product.fromCartItem() = getItemCartOrInit().get(id)

    private fun getItemCartOrInit(): SparseArray<CartItem> {
        if (cartItems == null) cartItems = SparseArray()
        return cartItems!!
    }

}

fun <E> SparseArray<E>.asList(): ArrayList<E> {
    val arrayList= ArrayList<E>(this.size())
    for (i in 0 until this.size()) arrayList.add(this.valueAt(i))
    return arrayList
}
