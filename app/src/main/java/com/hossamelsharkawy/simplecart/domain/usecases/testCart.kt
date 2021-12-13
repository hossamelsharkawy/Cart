package com.hossamelsharkawy.simplecart.domain.usecases

import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime

/*
CartItems:1000
0 :21.861446ms
1 :23.086540ms
2 :7.516821ms
3 :7.226575ms
4 :7.376618ms
showAllCartItems: 77.169824ms
0 :93.172304ms
1 :57.624571ms
2 :71.542132ms
3 :29.678919ms
4 :60.556431ms
showAllCartItems2: 313.013934ms
* */


/*
CartItems:20000
0 :25.034103ms
1 :23.803634ms
2 :8.478526ms
3 :8.464777ms
4 :18.786551ms
showAllCartItems: 94.571961ms
0 :67.834884ms
1 :66.483675ms
2 :45.294514ms
3 :61.348317ms
4 :60.412221ms
showAllCartItems2: 301.855630ms
*
* */


/*
CartItems:500000
0 :88.348322ms
1 :98.196169ms
2 :47.177218ms
3 :42.388292ms
4 :68.334442ms
showAllCartItems: 354.885390ms
0 :60.006922ms
1 :62.678903ms
2 :62.320469ms
3 :66.450043ms
4 :62.003163ms
showAllCartItems2: 313.959913ms

*
* */

/*
*
CartItems:1000000
0 :135.134767ms
1 :113.846847ms
2 :65.372600ms
3 :90.266941ms
4 :101.723555ms
showAllCartItems: 517.974641ms
0 :124.628108ms
1 :83.758272ms
2 :118.175364ms
3 :61.786083ms
4 :111.843204ms
showAllCartItems2: 500.667071ms
* */

@ExperimentalTime
fun main() {
    testShowAllCartItems()
}

@ExperimentalTime
fun testShowAllCartItems() {

    runBlocking {
        println("CartItems:" + showAllCartItems(productsRepo, cartRepo).size)
    }

    testRun("showAllCartItems") { showAllCartItems(productsRepo, cartRepo) }
    testRun("showAllCartItems2") { showAllCartItems2(productsRepo, cartRepo) }

}




suspend fun showAllCartItems2(
    productsRepo: IProductsRepository,
    cartRepo: ICartRepository,
) =
    productsRepo
        .getProducts()
        ?.associateBy { it.id } // convert productList to map by productId
        ?.run {
            cartRepo
                .getCartItems()
                ?.filter { c ->
                    this.containsKey(c.itemId) // check if the product in cartItem is exist productsMap.
                }
                ?.map { c -> // map cartItem to product.
                    this[c.itemId]!!
                        .apply {
                            this.qtyInCart = c.qty
                        }
                }
        } ?: arrayListOf()

