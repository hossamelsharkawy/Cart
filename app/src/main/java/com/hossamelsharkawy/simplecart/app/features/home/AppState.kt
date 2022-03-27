package com.hossamelsharkawy.simplecart.app.features.home

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hossamelsharkawy.simplecart.app.Route
import com.hossamelsharkawy.simplecart.app.features.cart.CartEvent
import com.hossamelsharkawy.simplecart.app.features.cart.createCartEvent
import com.hossamelsharkawy.simplecart.app.features.products.ProductViewModel
import com.hossamelsharkawy.simplecart.app.features.products.ProductsEvent
import com.hossamelsharkawy.simplecart.app.features.products.createProductEvent
import com.hossamelsharkawy.simplecart.data.entities.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class AppState @OptIn(ExperimentalMaterialApi::class) constructor(
    val viewModel: ProductViewModel,
    val productsEvent: ProductsEvent,
    val cartEvent: CartEvent,
    internal val bottomSheetState: ModalBottomSheetState,
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
    val scope: CoroutineScope,
    val routeState: State<Route<*>>
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberAppState(
    productViewModel: ProductViewModel = viewModel(),
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


fun AppState.getCurrentProductInfo(): Product? =
    if (routeState.value is Route.ProductInfo)
        (routeState.value as Route.ProductInfo).data
    else null


@OptIn(ExperimentalMaterialApi::class)
fun AppState.openBottomSheet() {
    scope.launch {

        bottomSheetState.snapTo(ModalBottomSheetValue.Expanded)
        // bottomSheetState.show()
    }
}
