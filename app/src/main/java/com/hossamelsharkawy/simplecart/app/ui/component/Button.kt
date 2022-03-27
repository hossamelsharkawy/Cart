package com.hossamelsharkawy.simplecart.app.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

val btnHeight = 40.dp
val btnWidth = 60.dp
val btnHeightSmall = 30.dp
val btnWidthSmall = 30.dp

@Composable
fun MyIconButton(
    imageVector: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .size(width = btnWidth, height = btnHeight)
            .then(modifier),
        onClick = onClick
    ) {
        Icon(
            imageVector,
            "",
            tint = tint,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun MyIconButtonSmall(
    imageVector: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier
            .width(btnWidthSmall)
            .then(modifier),
        onClick = onClick
    ) {
        Icon(
            imageVector,
            "",
            tint = tint,
            modifier = modifier


            /* .then(
                 modifier.wrapContentHeight(Alignment.Bottom).wrapContentSize(Alignment.Center)
             )*/
        )
    }
}

@Composable
fun MyButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(text)
    }
}


@Composable
fun MyButtonMaxWidth(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    MyButton(
        text = text,
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .then(modifier)
    )
}
