package com.hossamelsharkawy.simplecart.app.features.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hossamelsharkawy.simplecart.app.features.cart.CartScreen
import com.hossamelsharkawy.simplecart.app.features.products.ProductListScreen
import com.hossamelsharkawy.simplecart.app.features.products.ProductListSimpleScreen
import com.hossamelsharkawy.simplecart.app.features.products.ProductsListGridItemsScreen

//https://joebirch.co/android/modular-navigation-with-jetpack-compose/
@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.homeNavGraph(
    appState: AppState
) {
    val viewModel = appState.viewModel

    navigation(
        startDestination = BottomNavItem.Home.screen_route,
        route = HOME_GRAPH_ROUTE
    ) {
        composable(BottomNavItem.Search.screen_route) {

            val minRowWidthInDP = 129 // dp

            val spanCont = LocalConfiguration.current.screenWidthDp / minRowWidthInDP
            val productsMapState = remember { viewModel.itemsByCategoryState }
            viewModel.setRowCount(spanCont)

            ProductListScreen(
                productsEvents = appState.productsEvent,
                productsMapState,
                spanCont
            )
        }

        composable(BottomNavItem.Cart.screen_route) {
            CartScreen(
                viewModel.cart,
                appState.cartEvent
            )
        }

        composable(BottomNavItem.Order.screen_route) {
            val minRowWidthInDP = 129 // dp

            val spanCont = LocalConfiguration.current.screenWidthDp / minRowWidthInDP
            viewModel.setRowCount(spanCont)

            ProductsListGridItemsScreen(
                productsEvents = appState.productsEvent,
                gridItems = viewModel.itemsByCategoryStateFlat,
                spanCont = spanCont
            )
        }
        composable(BottomNavItem.Notification.screen_route) {
            val minRowWidthInDP = 150 // dp

            val spanCont = LocalConfiguration.current.screenWidthDp / minRowWidthInDP
            viewModel.setRowCount(spanCont)

            ProductsListGridItemsScreen(
                productsEvents = appState.productsEvent,
                gridItems = viewModel.itemsByCategoryStateFlat,
                spanCont = spanCont
            )

        }
        composable(BottomNavItem.Home.screen_route) {
            viewModel.setRowCount(1)
            ProductListSimpleScreen(
                productsEvents = appState.productsEvent,
                products = viewModel.itemsState,

            )
        }
    }
}


/*@ExperimentalMaterialApi
@Composable
fun bottomAppBar(
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
    BottomAppBar(modifier = Modifier) {

        IconButton(onClick = { coroutineScope.launch { sheetState.show() } })
        {
            Icon(Icons.Filled.Menu, contentDescription = "Localized description")
        }
    }
}*/
