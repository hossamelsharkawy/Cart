package com.hossamelsharkawy.simplecart.domain.usecases

import com.hossamelsharkawy.simplecart.data.source.local.FruitsCategory
import com.hossamelsharkawy.simplecart.data.entities.CartItem
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


val productsRepo = object : IProductsRepository {

    private val products = arrayListOf<Product>()
        .apply {
            repeat(1_000_000) {
                add(Product(id = it, category = FruitsCategory))
            }
        }

    override suspend fun getProducts(forceUpdate: Boolean) = products
    //arrayListOf(Product(1), Product(2), Product(3))
}

val cartRepo = object : ICartRepository {

    private val cartItems = arrayListOf<CartItem>()
        .apply {
            repeat(1_000_000) {
                it
                    .takeIf {
                        it.rem(10000) == 1
                        // true
                    }
                    ?.let {
                        add(CartItem(itemId = it))
                    }
            }
        }

    override suspend fun getCartItems() =
        cartItems
    //  arrayListOf(CartItem(1, 5), CartItem(3, 78))
    //null

    override suspend fun addNewCartItem(product: Product): CartItem {
        TODO("Not yet implemented")
    }

}


val repeat = 5


@ExperimentalTime
fun <T> testRun(string: String, block: suspend () -> T) = runBlocking {
    println(string.plus(": ").plus(measureTime {
        repeat(repeat) {
            println("$it :" + measureTime {
                block()
            })
        }

    }))
}
