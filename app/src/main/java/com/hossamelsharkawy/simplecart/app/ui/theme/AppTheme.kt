package com.hossamelsharkawy.simplecart.app.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


object MyColor {
    val backgroundColor = Color(0xFFE1EEE2)

}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = Color(0xFF4CAF50),
            secondary = Color(0xFFFF5722),
            surface = Color(0xFFFFFFFF),
            primaryVariant = Color(0xFF673AB7),
            background = MyColor.backgroundColor,
        ), typography = MyTypography
    ) {
        content()
    }
}
