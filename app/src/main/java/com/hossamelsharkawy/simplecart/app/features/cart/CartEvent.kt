package com.hossamelsharkawy.simplecart.app.features.cart

import com.hossamelsharkawy.simplecart.app.features.products.ProductViewModel
import com.hossamelsharkawy.simplecart.app.features.products.ProductsEvent
import com.hossamelsharkawy.simplecart.data.entities.Product

interface CartEvent : ProductsEvent {
    fun onRemoveAll()
    fun onCheckOut()
}


fun ProductViewModel.createCartEvent() = object : CartEvent {

    override fun onRemoveAll() {
        clearCart()
    }

    override fun onCheckOut() {

    }

    override fun onAddItemClick(product: Product) {
        addToCart(product)
    }

    override fun onRemoveItemClick(product: Product) {
        minQty(product)
    }

    override fun onProductInfoClick(product: Product) {
        openProductInfo(product)
    }

    override fun onSelectQtyClick(qty: Int, product: Product) {
        setQty(qty, product)
    }
}
