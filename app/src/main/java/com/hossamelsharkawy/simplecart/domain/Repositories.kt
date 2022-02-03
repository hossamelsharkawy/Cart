package com.hossamelsharkawy.simplecart.domain

import com.hossamelsharkawy.simplecart.data.entities.*
import kotlinx.coroutines.flow.Flow

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

//for business
interface ICartRepository {
    suspend fun getCartItems(): CartItems?
    suspend fun addNewCartItem(product: Product): CartItem
    suspend fun increaseCartItemQty(product: Product): CartItem? = null
    suspend fun decreaseCartItemQty(product: Product): CartItem? = null
    suspend fun isCartValid(): Boolean = true
    suspend fun restoreFromLocalSource(): CartItems? = null
    suspend fun clear(): Nothing? = null
}

interface ICartRepositoryFlow {
    suspend fun getCartItems(): Flow<CartItems>?
    suspend fun addNewCartItem(product: Product): Flow<CartItems>
    suspend fun increaseCartItemQty(product: Product): Flow<CartItems>
    suspend fun decreaseCartItemQty(product: Product): Flow<CartItem>
    suspend fun restoreFromLocalSource(): Flow<CartItem>
}

//for tech
interface ICartDataSource {
    suspend fun getCartItemsList(): CartItems? = null
    suspend fun saveCartItem(cartItems: CartItem): CartItem? = null
    suspend fun removeCartItem(cartItem: CartItem): CartItem? = null
    suspend fun removeAllCartItems() {}
    suspend fun getLastEditTime(): String? = null
}

interface IFavoriteRepository {
    suspend fun getFavoriteList(): Favorites? = null
    suspend fun addToFav(product: Favorite) {}
    suspend fun removeFromFav(product: Product) {}
}





