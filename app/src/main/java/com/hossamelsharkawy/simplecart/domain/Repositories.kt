package com.hossamelsharkawy.simplecart.domain

import com.hossamelsharkawy.simplecart.data.entities.*

interface IProductsDataSource {
    @Throws(Exception::class)
    suspend fun getProducts(): Products? = null
    suspend fun deleteAll() {}
    suspend fun save(product: Product) {}
}

interface IProductsRepository {
    suspend fun getProducts(forceUpdate: Boolean = true): Products? = null
    suspend fun deleteAll() {}
    suspend fun save(product: Product) {}
}

interface ICartRepository {
    suspend fun getCartItems(): CartItems?
    suspend fun addToCart(product: Product): CartItem
    suspend fun plusQtyInCart(product: Product): CartItem
    suspend fun minQtyInCart(product: Product): CartItem? = null
    suspend fun isCartValid(): Boolean = true
    suspend fun resetCart() {}
}

interface ICartDataSource {
    suspend fun getCartItemsList(): CartItems? = null
    suspend fun saveCartItem(cartItems: CartItem): CartItem? = null
    suspend fun removeCartItem(cartItem: CartItem): CartItem? = null
    suspend fun getLastEditTime():String? =null
}

interface IFavoriteRepository {
    suspend fun getFavoriteList(): Favorites? = null
    suspend fun addToFav(product: Favorite) {}
    suspend fun removeFromFav(product: Product) {}
}





