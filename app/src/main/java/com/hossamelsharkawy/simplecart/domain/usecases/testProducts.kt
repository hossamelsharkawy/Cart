package com.hossamelsharkawy.simplecart.domain.usecases

import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import kotlinx.coroutines.runBlocking
import kotlin.time.ExperimentalTime

/*
Products:1000000
CartItems:100
0 :25.708243ms
1 :15.701081ms
2 :15.681330ms
3 :15.189718ms
4 :15.879522ms
showAllProducts2 :98.680939ms
0 :69.378701ms
1 :120.935977ms
2 :82.006330ms
3 :55.536976ms
4 :72.751709ms
showAllProducts3 :401.257633ms
0 :165.151638ms
1 :125.203779ms
2 :91.997233ms
3 :98.807536ms
4 :71.874241ms
showAllProducts4 :553.393215ms
* */


/*
Products:1000000
CartItems:20000
0 :33.515191ms
1 :16.949041ms
2 :15.936875ms
3 :16.786477ms
4 :16.507097ms
showAllProducts2 :109.454567ms
0 :102.320616ms
1 :105.288925ms
2 :93.498997ms
3 :72.149988ms
4 :66.435384ms
showAllProducts3 :440.247762ms
0 :169.746273ms
1 :155.645336ms
2 :97.783086ms
3 :73.936191ms
4 :103.671771ms
showAllProducts4 :601.230414ms
* */


/*
Products:1000000
CartItems:500000
0 :46.187519ms
1 :81.698578ms
2 :51.874757ms
3 :36.614981ms
4 :62.936210ms
showAllProducts2 :289.998148ms
0 :104.416183ms
1 :63.806333ms
2 :85.591235ms
3 :100.169825ms
4 :68.651688ms
showAllProducts3 :423.244371ms
0 :291.045254ms
1 :212.595026ms
2 :187.217003ms
3 :166.757350ms
4 :169.601659ms
showAllProducts4 :1.027567567s
* */


@ExperimentalTime
fun main() {
    runBlocking {
        println("Products:" + showAllProducts2(productsRepo, cartRepo).size)
        println("CartItems:" + showAllCartItems(productsRepo, cartRepo).size)

    }

    testRun("showAllProducts2") { showAllProducts2(productsRepo, cartRepo) }
    testRun("showAllProducts3") { showAllProducts3(productsRepo, cartRepo) }
    testRun("showAllProducts4") { showAllProducts4(productsRepo, cartRepo) }


/*runBlocking {
    println(showAllProducts2(productsRepo, cartRepo))
    println(showAllProducts3(productsRepo, cartRepo))
    println(showAllProducts4(productsRepo, cartRepo))
}*/
}


//36.381us
suspend fun showAllProducts2(
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
        }
        ?: productsRepo.getProducts()
        ?: arrayListOf()

//1.582033ms
suspend fun showAllProducts3(
    productsRepo: IProductsRepository,
    cartRepo: ICartRepository,
): Products =
    productsRepo
        .getProducts()
        ?.associateBy { it.id }
        ?.apply {
            cartRepo
                .getCartItems()
                ?.map { c ->
                    this[c.itemId]?.apply { qtyInCart = c.qty }
                }
        }?.values
        ?.toList()
        ?: arrayListOf()

//13.258169ms
suspend fun showAllProducts4(
    productsRepo: IProductsRepository,
    cartRepo: ICartRepository,
): Products =
    cartRepo
        .getCartItems()
        ?.associateBy { it.itemId }
        ?.run {
            productsRepo
                .getProducts()
                ?.filter { this.containsKey(it.id) }
                ?.map { p ->
                    p.apply { qtyInCart = this@run[p.id]?.qty ?: 0 }
                }
        }
        ?.plus(productsRepo.getProducts() ?: arrayListOf())
        ?.distinctBy { it.id }
        ?.sortedBy { it.id }

        ?: productsRepo.getProducts()
        ?: arrayListOf()




