package com.hossamelsharkawy.simplecart.app.features.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hossamelsharkawy.simplecart.app.Route
import com.hossamelsharkawy.simplecart.app.features.cart.CartScreen
import com.hossamelsharkawy.simplecart.app.features.products.ProductDetails
import com.hossamelsharkawy.simplecart.app.ui.theme.MyColor


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
                        CartScreen(
                            viewModel.cart,
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

