package com.hossamelsharkawy.simplecart.app

import com.hossamelsharkawy.simplecart.data.entities.Product
import kotlinx.coroutines.flow.MutableSharedFlow


sealed class Route<T>(val route: RouteNames) {
    object ProductsList : Route<Nothing>(RouteNames.ProductsList)
    object Notification : Route<Nothing>(RouteNames.Notification)
    object CartItems : Route<Nothing>(RouteNames.CartItems)
    class ProductInfo(val data: Product) : Route<Product>(RouteNames.ProductInfo)
}

enum class RouteNames { ProductsList,Notification, CartItems ,ProductInfo }


class UIRouter {
    val navAction = MutableSharedFlow<Route<*>>()

    private suspend fun navTo(route: Route<*>) {
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

    suspend fun navToProductInfo(product: Product) {
        navAction.emit(Route.ProductInfo(product))
    }

}

