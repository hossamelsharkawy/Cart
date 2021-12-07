package com.hossamelsharkawy.simplecart

import com.hossamelsharkawy.base.extension.launchOnUI
import com.hossamelsharkawy.simplecart.data.entities.CartItem
import com.hossamelsharkawy.simplecart.data.entities.CartItems
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import com.hossamelsharkawy.simplecart.domain.usecases.showAllProducts
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun aa() {

        val productRepository = object : IProductsRepository {

        }

        val crtRepository = object : ICartRepository {

            override suspend fun getCartItems(): CartItems? {
                TODO("Not yet implemented")
            }

            override suspend fun addNewCartItem(product: Product): CartItem {
                TODO("Not yet implemented")
            }

        }

        launchOnUI {
            val xx = showAllProducts(productRepository, crtRepository)
        }

    }
}

