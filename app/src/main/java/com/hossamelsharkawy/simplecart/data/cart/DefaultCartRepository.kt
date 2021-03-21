package com.hossamelsharkawy.simplecart.data.cart

import com.hossamelsharkawy.base.extension.toCalendar
import com.hossamelsharkawy.base.extension.toMaxCalendar
import com.hossamelsharkawy.simplecart.data.entities.CartItem
import com.hossamelsharkawy.simplecart.data.entities.CartItems
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.domain.ICartDataSource
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import javax.inject.Inject

class DefaultCartRepository @Inject constructor(
    private val cartDataSource: ICartRepository,
    private val cartLocalDataSource: ICartDataSource
) : ICartRepository {
    private val mutex = Mutex()

    override suspend fun getCartItems(): CartItems? =
        cartDataSource.getCartItems()
            ?: cartLocalDataSource.getCartItemsList()
                ?.takeIf {
                    if (isCartValid()) {
                        true
                    } else {
                        resetCart()
                        false
                    }
                }

    override suspend fun addToCart(product: Product): CartItem = mutex.withLock {
        return cartDataSource
            .addToCart(product.apply { qtyInCart = 1 })
            .saveInLocal()
    }

    override suspend fun plusQtyInCart(product: Product): CartItem = mutex.withLock {
        return cartDataSource
            .plusQtyInCart(product.apply { qtyInCart++ })
            .saveInLocal()
    }

    override suspend fun minQtyInCart(product: Product): CartItem? = mutex.withLock {
        return cartDataSource.minQtyInCart(product.apply { qtyInCart-- })
            ?.saveInLocal()
            ?: product.removeFromLocal()
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





