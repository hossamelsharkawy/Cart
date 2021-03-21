package com.hossamelsharkawy.simplecart.domain.usecases

import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository


suspend fun showAllProducts(
    productsRepo: IProductsRepository,
    cartRepo: ICartRepository,
): Products? =
    productsRepo.getProducts().apply {
        cartRepo.getCartItems()
            ?.forEach { cartItem ->
                this?.find { it.id == cartItem.itemId }
                    ?.qtyInCart = cartItem.qty
            }
    }



