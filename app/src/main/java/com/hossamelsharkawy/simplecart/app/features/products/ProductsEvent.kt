package com.hossamelsharkawy.simplecart.app.features.products

import com.hossamelsharkawy.simplecart.data.entities.Product

interface ProductsEvent {
    fun onAddItemClick(product: Product)
    fun onRemoveItemClick(product: Product)
    fun onProductInfoClick(product: Product)
    fun onSelectQtyClick(qty: Int, product: Product)
}


fun ProductViewModel.createProductEvent() = object : ProductsEvent {
    override fun onAddItemClick(product: Product) {
        plusQty(product)
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