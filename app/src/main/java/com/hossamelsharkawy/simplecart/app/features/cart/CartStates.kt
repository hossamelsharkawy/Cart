package com.hossamelsharkawy.simplecart.app.features.cart

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.hossamelsharkawy.simplecart.data.entities.Product

object CartStates {

    var cartItemsState = mutableStateListOf<Product>()
        private set

    var itemsCountState = mutableStateOf("")
        private set

    var subPriceState = mutableStateOf(0.0)
        private set

    var totalPriceState = mutableStateOf(0.0)
        private set

    var deliveryFeeState = mutableStateOf(0.0)
        private set

    var deliveryAddressState = mutableStateOf("")
        private set


}