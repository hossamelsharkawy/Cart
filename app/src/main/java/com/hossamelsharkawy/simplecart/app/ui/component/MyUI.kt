package com.hossamelsharkawy.simplecart.app.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/*************************************************************/


/*************************************************************/

// Center
@Composable
fun MyRowCenter(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        Modifier
            // .padding(top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.Center)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        content = content
    )
}

// Center
@Composable
fun MyRowStart(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        Modifier
            // .padding(top = 20.dp, bottom = 1.dp)
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.BottomStart)
            .then(modifier),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Start,
        content = content
    )
}

@Composable
fun MyColumnCenter(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
fun MyCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit

) {
    Card(
        elevation = 2.dp,
        modifier = modifier.then(Modifier.padding(1.9.dp)),
        content = content
        //.wrapContentSize()
        // .height(194.dp)
        // .fillMaxHeight()
        //.aspectRatio(0.7f)
    )
}

