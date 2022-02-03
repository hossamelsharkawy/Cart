package com.hossamelsharkawy.simplecart.data.entities

import com.hossamelsharkawy.simplecart.app.features.products.getBaseImageUrl
import com.hossamelsharkawy.simplecart.app.features.products.kiloUnit
import java.io.Serializable

typealias Products = List<Product>

data class Product(
    var id: Int,
    var title: String? = null,
    private var image: String? = null,

    var price: String? = null,
    //private var price_new: String? = null,
    var has_discount: Boolean = false,
    var qtyInCart: Int = 0,
    var category: Category,
    var unit: Unit = kiloUnit,
    var relatedRecipes: List<Recipe>? = null
) {

    val cartPrice: Float? get() = price?.toFloat()?.times(qtyInCart)

    override fun toString() = "ID:$id QTY:$qtyInCart "

    override fun hashCode() = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false

        return true
    }

    private fun getImage(size: Int) = getBaseImageUrl(size).plus(image)

  fun getLargeImage() = getImage(700)
    //fun getLargeImage() = ""
    fun getSmallImage() = getImage(200)
}




typealias CartItems = List<CartItem>

data class CartItem(
    val itemId: Int,
    var qty: Int = 0
) : Serializable {

    override fun toString() = "$itemId"
}



typealias Favorites = List<Favorite>

data class Favorite(
    val itemId: Long? = null
) : Serializable


data class Category(var id: String, var maxView: Boolean = true)
data class Recipe(var id: String)

sealed class Unit(var value: Int, val name: String) {
    class GramUnit(value: Int, name: String = "Gr") : Unit(value, name)
    class KUnit(value: Int, name: String = "Kg") : Unit(value, name)
    class PackUnit(value: Int, name: String = "Pack") : Unit(value, name)

    fun string() = "$value $name"
}


const val priceFormat = "%.1f"
const val Currency = "$"
const val PercentageMark = "%"
fun Float.toPriceString() = Currency.plus(priceFormat.format(this))
fun Float.round() = priceFormat.format(this).toFloat()






