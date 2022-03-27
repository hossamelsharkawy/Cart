package com.hossamelsharkawy.simplecart.app.features.home

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import com.hossamelsharkawy.simplecart.app.ui.theme.MyTypography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeTopAppBar(
    appState: AppState,
    cartCount: String = appState.viewModel.cart.itemsCountState.value,
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