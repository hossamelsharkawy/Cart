package com.hossamelsharkawy.simplecart.app.demo

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.hossamelsharkawy.simplecart.app.features.home.MainActivity
import com.hossamelsharkawy.simplecart.data.cart.CartLocalDataSource
import com.hossamelsharkawy.simplecart.data.entities.CartItem
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import com.hossamelsharkawy.simplecart.domain.usecases.showAllCartItems
import com.hossamelsharkawy.simplecart.domain.usecases.showAllProducts


fun MainActivity.tesCart(
    cartLocalDataSource: CartLocalDataSource,
    cartRepository: ICartRepository,
    productsRepository: IProductsRepository
) {


    lifecycleScope.launchWhenCreated {
        cartLocalDataSource.clearCartItems()

        val cartItem1 = CartItem(1, 1)
        cartLocalDataSource.saveCartItem(cartItem1)

        val cartItems = showAllCartItems(productsRepository, cartRepository)
        Log.d("cartPreferences cart", cartItems.toString())

        val products = showAllProducts(productsRepository, cartRepository)
        Log.d("cartPreferences products", products.toString())

    }
}


fun MainActivity.tesCartIsValid(
    cartRepository: ICartRepository,
) {

    lifecycleScope.launchWhenCreated {


        Log.d("cartPreferences cart", cartRepository.isCartValid().toString())


    }
}