package com.hossamelsharkawy.simplecart.app.features.home


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.hossamelsharkawy.simplecart.app.ui.theme.AppTheme
import com.hossamelsharkawy.simplecart.app.util.ProvideImageLoader
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            ProvideWindowInsets {
                ProvideImageLoader {
                    AppTheme {
                        Surface {
                            AppHome()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
@ExperimentalMaterialApi
fun appHomeP() {
    AppHome()
}


/*
class MainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme{
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Route.CartItems.name) {
                    composable(Route.Splash) {
                        Screen(
                            title = Route.Splash,
                            // This creates a fatal error. I can't navigate to nested graph
                            onNavigation = { navController.navigate(Route.Auth) }
                        )
                    }
                    navigation(startDestination = Route.NestedAuth.Social, route = Route.Auth){
                        composable(Route.NestedAuth.Social) {
                            Screen(
                                title = Route.NestedAuth.Social,
                                onNavigation = { navController.navigate(Route.NestedAuth.EmailSignup) }
                            )
                        }

                        composable(Route.NestedAuth.EmailSignup) {
                            Screen(
                                title = Route.NestedAuth.EmailSignup,
                                onNavigation = { navController.navigate(Route.Main) }
                            )
                        }
                    }
                    composable(Route.Main) {
                        Screen(
                            title = Route.Main,
                            onNavigation = { navController.navigate(Route.Splash) }
                        )
                    }
                }
            }
        }
    }
}*/
