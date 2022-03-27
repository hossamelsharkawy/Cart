package com.hossamelsharkawy.simplecart.app.features.home

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hossamelsharkawy.simplecart.app.RouteNames
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterialApi
@Composable
fun AppHome() {

    val appState = rememberAppState()

    /* when (appState.routeState.value) {
         //  is Route.ProductInfo -> appState.navController.navigate(RouteNames.ProductInfo.name)
         is Route.ProductInfo -> appState.openBottomSheet()
         else -> {}
     }*/

    val loadingState = remember { appState.viewModel.dataLoading }

    BackHandler {
        appState.scope.launch {
            if (appState.navController.backQueue.size > 0)
                appState.navController.popBackStack()
            // // viewModel.uIRoute.navToProductsList()
        }
    }
    CartBottomSheet(appState) {
        Scaffold(
            scaffoldState = appState.scaffoldState,
            // modifier = Modifier.statusBarsPadding(),
            //modifier = Modifier.statusNavPadding(),
            //modifier = Modifier.padding(50.dp),
            //  topBar = { HomeTopAppBar(appState) },
            bottomBar = {
                BottomNavigation(
                    navController = appState.navController,
                    appState.viewModel.cart.itemsCountState.value
                )
            }
            //    drawerContent = { AppDrawer() }

        ) { innerPaddingModifier ->

            NavHost(
                startDestination = HOME_GRAPH_ROUTE,
                route = ROOT_GRAPH_ROUTE,
                navController = appState.navController,
                modifier = Modifier
                    .padding(innerPaddingModifier)
                    .then(Modifier.padding(5.dp)),
            ) {
                homeNavGraph(appState)
                // authNavGraph(appState)
            }
            LoadingUI(loadingState.value)
        }
    }
}


private fun NavGraphBuilder.authNavGraph(
    appState: AppState
) {
    navigation(
        startDestination = RouteNames.ProductInfo.name,
        route = PRODUCT_GRAPH_ROUTE
    ) {
        composable(RouteNames.ProductInfo.name) {
            //ProductDetails(appState.getCurrentProductInfo(), appState.productsEvent)
        }
    }
}


@SuppressLint("ModifierFactoryExtensionFunction")
fun clickable(onClick: () -> Unit) = Modifier.clickable { onClick.invoke() }



