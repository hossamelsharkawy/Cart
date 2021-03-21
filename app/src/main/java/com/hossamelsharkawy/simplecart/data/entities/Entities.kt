package com.hossamelsharkawy.simplecart.data.entities

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.hossamelsharkawy.simplecart.BR
import java.io.Serializable

typealias Products = List<Product>

data class Product(
    var id: Int,
    var title: String? = null,
    var image: String? = null,

    var price: String? = null,
    var price_new: String? = null,
    var has_discount: Boolean = false,

    ) : BaseObservable() {
    @get:Bindable
    var qtyInCart: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.qtyInCart)
        }

    fun getItemCartPrice() = price_new?.toFloat() ?: 0f * qtyInCart

    fun getPriceString() = getItemCartPrice().toPriceString()

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (id != other.id) return false

        return true
    }

}




typealias CartItems = List<CartItem>

data class CartItem(
    val itemId: Int,
    var qty: Int = 0
) : Serializable



typealias Favorites = List<Favorite>

data class Favorite(
    val itemId: Long? = null
) : Serializable


data class Category(var id: String)


const val priceFormat = "%.1f"
const val Currency = "$"
const val PercentageMark = "%"
fun Float.toPriceString() = Currency.plus(priceFormat.format(this))
fun Float.round() = priceFormat.format(this).toFloat()






