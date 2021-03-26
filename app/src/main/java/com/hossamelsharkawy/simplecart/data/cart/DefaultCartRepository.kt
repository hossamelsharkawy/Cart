package com.hossamelsharkawy.simplecart.data.cart

import android.util.SparseArray
import com.hossamelsharkawy.base.extension.toCalendar
import com.hossamelsharkawy.base.extension.toMaxCalendar
import com.hossamelsharkawy.simplecart.data.entities.CartItem
import com.hossamelsharkawy.simplecart.data.entities.CartItems
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.data.source.remote.APIService
import com.hossamelsharkawy.simplecart.domain.ICartDataSource
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.http.GET
import java.util.*
import javax.inject.Inject

class DefaultCartRepository @Inject constructor(
    private val cartLocalDataSource: ICartDataSource
) : ICartRepository {
    private val mutex = Mutex()

    var cartItemsSparseArray: SparseArray<CartItem>? = null
        private set

    override suspend fun getCartItems(): CartItems? = mutex.withLock {
        return cartItemsSparseArray?.asList()
            ?: restoreFromLocalSource()
                .takeIf { isCartValid() }
    }

    override suspend fun addNewCartItem(product: Product): CartItem = mutex.withLock {
        return product
            .apply { qtyInCart++ }
            .addToCart()
            .saveInLocal()
    }

    override suspend fun increaseCartItemQty(product: Product): CartItem = mutex.withLock {
        return product
            .apply { qtyInCart++ }
            .addToCart()
            .saveInLocal()
    }

    override suspend fun decreaseCartItemQty(product: Product): CartItem? = mutex.withLock {
        return product.apply { qtyInCart-- }
            .minQtyOrRemove()
            ?.saveInLocal()
            ?: product.removeFromLocal()
    }

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
        if (cartItemsSparseArray == null) cartItemsSparseArray = SparseArray()
        return cartItemsSparseArray!!
    }


    override suspend fun restoreFromLocalSource(): CartItems? {
        cartLocalDataSource
            .getCartItemsList()
            ?.forEach {
                getItemCartOrInit().put(it.itemId, it)
            }
        return cartItemsSparseArray?.asList()
    }

    override suspend fun isCartValid() =
        cartLocalDataSource
            .getLastEditTime()
            ?.takeIf { it.isNotEmpty() }
            ?.toLong()
            ?.toCalendar()
            ?.toMaxCalendar(3)
            ?.after(Calendar.getInstance())
            ?: true

    private suspend fun Product.removeFromLocal(): Nothing? {
        cartLocalDataSource.removeCartItem(CartItem(itemId = id))
        return null
    }


    private suspend fun CartItem.saveInLocal() = cartLocalDataSource.saveCartItem(this)!!

}


fun <E> SparseArray<E>.asList(): ArrayList<E> {
    val arrayList = ArrayList<E>(this.size())
    for (i in 0 until this.size()) arrayList.add(this.valueAt(i))
    return arrayList
}





