package com.hossamelsharkawy.simplecart.domain.usecases

import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import kotlinx.coroutines.*

suspend fun showAllProducts(
    productsRepo: IProductsRepository,
    cartRepo: ICartRepository,
): Products =
    cartRepo
        .getCartItems()
        ?.associateBy { it.itemId }
        ?.run {
            productsRepo
                .getProducts()
                ?.map { p ->
                    p.apply { qtyInCart = this@run[p.id]?.qty ?: 0 }
                }
        }/*?.sortedBy {
          it.title?.length
        }*/
        ?: productsRepo.getProducts()
        ?: arrayListOf()






