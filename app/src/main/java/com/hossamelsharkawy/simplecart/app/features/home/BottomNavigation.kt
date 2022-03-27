package com.hossamelsharkawy.simplecart.app.features.home


import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.app.ui.theme.MyColor
import com.hossamelsharkawy.simplecart.app.ui.theme.MyTypography

const val ROOT_GRAPH_ROUTE = "root_graph"
const val AUTH_GRAPH_ROUTE = "auth_graph"
const val PRODUCT_GRAPH_ROUTE = "product_graph"
const val HOME_GRAPH_ROUTE = "home_graph"


sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
    object Home : BottomNavItem("Home", R.drawable.ic_shop_svgrepo_com, "home")
    object Cart : BottomNavItem("Cart", R.drawable.ic_cart, "cart")
    object Order : BottomNavItem("My Orders", R.drawable.ic_menu_gallery, "orderList")
    object Notification : BottomNavItem("Notification", R.drawable.ic_bell, "notification")
    object Search : BottomNavItem("Search", R.drawable.ic_search_gray, "search")
}


@Composable
fun BottomNavigation(
    navController: NavController,
    itemsCount: String
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Cart,
        BottomNavItem.Order,
        BottomNavItem.Notification,
        BottomNavItem.Search
    )
    BottomNavigation(
        elevation = 1.9.dp,
        backgroundColor = MyColor.BottomNavigationBackgroundColor,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    if (item is BottomNavItem.Cart) {
                        CartNavItem(item, itemsCount)
                    } else {
                        Icon(painterResource(id = item.icon), contentDescription = item.title)
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        //  style = MyTypography.subtitle1,
                        fontSize = 10.sp
                    )
                },
                selectedContentColor = Color.White,
                //unselectedContentColor = Color.Black.copy(0.4f),
                unselectedContentColor = Color.Gray,
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                                // inclusive = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun CartNavItem(item: BottomNavItem.Cart, itemsCount: String) {
    Box {
        Icon(
            painterResource(id = item.icon), contentDescription = item.title,
            modifier = Modifier
                .align(Alignment.BottomCenter),
        )
        CartCount(
            itemsCount = itemsCount,
            Modifier
                .align(Alignment.TopEnd)

        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CartCount(itemsCount: String, modifier: Modifier) {

    MyAnimatedContentCount(itemsCount) { targetCount ->
        Text(
            modifier = modifier
                //.shadow(elevation = 1.dp ,shape = RoundedCornerShape(1))
                .padding(
                    start = 40.dp
                )

            /* .border(
                 1.dp,
                 color = Color(0xFFFFFFFF),
                // shape = RoundedCornerShape(65.dp)
             )*/,
            text = targetCount,
            style = MyTypography.overline,
            color = MyColor.RedLite,
            fontSize = 20.sp
        )

    }

}

@ExperimentalAnimationApi
@Composable
fun  MyAnimatedContentCount(
    targetState: String,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentScope<String>.() -> ContentTransform = {
        if (targetState > initialState) {
            // If the target number is larger, it slides up and fades in
            // while the initial (smaller) number slides up and fades out.
            slideInVertically { height -> height } + fadeIn() with
                    slideOutVertically { height -> -height } + fadeOut()
        } else {
            // If the target number is smaller, it slides down and fades in
            // while the initial number slides down and fades out.
            slideInVertically { height -> -height } + fadeIn() with
                    slideOutVertically { height -> height } + fadeOut()
        }.using(
            // Disable clipping since the faded slide-in/out should
            // be displayed out of bounds.
            SizeTransform(clip = false)
        )
    },
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable() AnimatedVisibilityScope.(targetState: String) -> Unit
) {
    val transition = updateTransition(targetState = targetState, label = "AnimatedContent")
    transition.AnimatedContent(
        modifier,
        transitionSpec,
        contentAlignment,
        content = content
    )
}


