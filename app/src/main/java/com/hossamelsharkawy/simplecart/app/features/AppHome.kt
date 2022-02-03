package com.hossamelsharkawy.simplecart.app.features

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.hossamelsharkawy.simplecart.app.Route
import com.hossamelsharkawy.simplecart.app.RouteNames
import com.hossamelsharkawy.simplecart.app.features.products.*
import com.hossamelsharkawy.simplecart.app.ui.theme.MyColor
import com.hossamelsharkawy.simplecart.app.ui.theme.MyTypography
import com.hossamelsharkawy.simplecart.data.entities.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
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

    BackHandler {
        appState.scope.launch {
            //  appState.navController.popBackStack()
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
            bottomBar = { BottomNavigation(navController = appState.navController) },
            //    drawerContent = { AppDrawer() }

        ) { innerPaddingModifier ->

            NavHost(
                startDestination = HOME_GRAPH_ROUTE,
                route = ROOT_GRAPH_ROUTE,
                navController = appState.navController,
                modifier = Modifier.padding(innerPaddingModifier),
            ) {
                homeNavGraph(appState)
                // authNavGraph(appState)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.homeNavGraph(
    appState: AppState
) {
    navigation(
        startDestination = BottomNavItem.Home.screen_route,
        route = HOME_GRAPH_ROUTE
    ) {

        val viewModel = appState.viewModel

        composable(BottomNavItem.Home.screen_route) {
            ProductListUI(viewModel.itemsState, appState.productsEvent)
        }

        composable(BottomNavItem.Cart.screen_route) {
            CartUI(
                viewModel.itemsCount.value,
                viewModel.cartItemsState, appState.cartEvent
            )
        }

        composable(BottomNavItem.Order.screen_route) {
            ProductDetails(
                item = appState.getCurrentProductInfo() ?: fakeProduct,
                productsEvent = fakeCartEvent
            )
        }
        composable(BottomNavItem.Notification.screen_route) { Text(text = "4") }
        composable(BottomNavItem.Search.screen_route) { Text(text = "5") }
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


@Stable
class AppState @OptIn(ExperimentalMaterialApi::class) constructor(
    val viewModel: ProductViewModel1,
    val productsEvent: ProductsEvent,
    val cartEvent: CartEvent,
    internal val bottomSheetState: ModalBottomSheetState,
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
    val scope: CoroutineScope,
    val routeState: State<Route<*>>
)

fun AppState.getCurrentProductInfo(): Product? =
    if (routeState.value is Route.ProductInfo)
        (routeState.value as Route.ProductInfo).data
    else null


@OptIn(ExperimentalMaterialApi::class)
private fun AppState.openBottomSheet() {
    scope.launch {

        bottomSheetState.snapTo(ModalBottomSheetValue.Expanded)
        // bottomSheetState.show()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberAppState(
    productViewModel: ProductViewModel1 = viewModel(),
): AppState {
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val routeState =
        productViewModel.uIRoute.navAction.collectAsState(initial = Route.ProductsList)



    return remember {
        AppState(
            productViewModel,
            productViewModel.createProductEvent(),
            productViewModel.createCartEvent(),
            modalBottomSheetState,
            navController,
            scaffoldState,
            scope,
            routeState
        )
    }
}


@ExperimentalMaterialApi
@Composable
fun bottomAppBar(
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
    BottomAppBar(modifier = Modifier) {
        IconButton(
            onClick = {
                coroutineScope.launch { sheetState.show() }
            }
        ) {
            Icon(Icons.Filled.Menu, contentDescription = "Localized description")
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeTopAppBar(
    appState: AppState,
    cartCount: Int = appState.viewModel.itemsCount.value,
    onCartClick: () -> Unit = {
        appState.scope.launch {
            appState.viewModel.uIRoute.navToCartItems()
        }
    },
    openDrawer: () -> Unit = {
        appState.scope.launch { appState.scaffoldState.drawerState.open() }
    }
) {
    TopAppBar(
        title = {
            Text(
                text = "TotalCount:${cartCount}",
                clickable { onCartClick() },
                style = MyTypography.h1,
            )
        },
        navigationIcon = {
            Icon(
                Icons.Filled.Menu,
                contentDescription = "Localized description",
                clickable { openDrawer() }
            )
        }
    )
}

@SuppressLint("ModifierFactoryExtensionFunction")
private fun clickable(onClick: () -> Unit) = Modifier.clickable { onClick.invoke() }


@ExperimentalMaterialApi
@Composable
fun CartBottomSheet(
    appState: AppState,
    content: @Composable () -> Unit
) {

    with(appState) {
        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetBackgroundColor = MyColor.backgroundColor,
            sheetContent = {

                when (appState.routeState.value) {
                    is Route.ProductInfo -> {
                        appState.openBottomSheet()
                        ProductDetails(
                            appState.getCurrentProductInfo()!!,
                            appState.productsEvent
                        )
                    }
                    is Route.CartItems -> {
                        appState.openBottomSheet()
                        CartUI(
                            viewModel.itemsCount.value,
                            viewModel.cartItemsState,
                            cartEvent
                        )
                    }
                    else -> {
                        Spacer(Modifier.padding(50.dp))
                    }
                }

            }
        ) {
            content()
        }
    }
}


fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}
