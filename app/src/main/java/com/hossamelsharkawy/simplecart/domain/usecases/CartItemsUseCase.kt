package com.hossamelsharkawy.simplecart.domain.usecases

import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository


/*suspend fun showAllCartItems(
    productsRepo: IProductsRepository,
    cartRepository: ICartRepository,
): Products = showAllProducts(productsRepo, cartRepository).filter { it.qtyInCart > 0 }*/


suspend fun Product.addToCart(cartRepository: ICartRepository) {
    cartRepository.addNewCartItem(this)
}

suspend fun Product.plusQtyInCart(cartRepository: ICartRepository) {
    cartRepository.increaseCartItemQty(this)
}

suspend fun Product.minQtyInCart(cartRepository: ICartRepository) {
    cartRepository.decreaseCartItemQty(this)
}


suspend fun showAllCartItems(
    productsRepo: IProductsRepository,
    cartRepo: ICartRepository,
): Products =
    cartRepo
        .getCartItems()
        ?.associateBy { it.itemId }
        ?.run {
            productsRepo
                .getProducts()
                ?.filter { c ->
                    this.containsKey(c.id) // check if the product in cartItem is exist productsMap.
                }
                ?.map { p ->
                    p.apply { qtyInCart = this@run[p.id]?.qty ?: 0 }
                }
        } ?: arrayListOf()




