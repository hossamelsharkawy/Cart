package com.hossamelsharkawy.simplecart.app.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


object MyColor {
    val RedLite = Color(0xFFFFC6C1)
    val backgroundColor = Color(0xFFE1EEE2)
    val BottomNavigationBackgroundColor = Color(0xFF0B1423)
    val BlueDark = Color(0xFF142646)
    val BlueLite = Color(0xFF1E3968)
    val white = Color(0xFFFCFDFF)
    val green = Color(0xFF26962B)
    val GreenLite = Color(0x5726962B)
    val red = Color(0xFF9C1C40)
    val Transparint = Color(0xB4F7F7F7)

}

@Composable
fun AppTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = lightColors(
            secondaryVariant = Color(0xFF0C610F),
            primary = Color(0xFF26962B),
            secondary =MyColor.green,
            surface = Color(0xFFFFFFFF),
            primaryVariant = Color(0xFF673AB7),
            background = MyColor.backgroundColor,
        ), typography = MyTypography
    ) {
        content()
    }
}
