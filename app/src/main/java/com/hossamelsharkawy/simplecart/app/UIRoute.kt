package com.hossamelsharkawy.simplecart.app

import kotlinx.coroutines.flow.MutableSharedFlow


enum class Route {
    ProductsList, Notification, CartItems, ProductInfo
}

class UIRouter {
    val navAction = MutableSharedFlow<Route>()

    suspend fun navTo(route: Route) {
        navAction.emit(route)
    }

    suspend fun navToNotification() {
        navAction.emit(Route.Notification)
    }

    suspend fun navToProductsList() {
        navAction.emit(Route.ProductsList)
    }

    suspend fun navToCartItems() {
        navAction.emit(Route.CartItems)
    }

}

