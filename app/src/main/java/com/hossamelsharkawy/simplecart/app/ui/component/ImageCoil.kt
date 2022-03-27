package com.hossamelsharkawy.simplecart.app.ui.component

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoilApi::class, kotlinx.coroutines.DelicateCoroutinesApi::class)
@Composable
fun MyImageLoadingCoil(
    url: String,
    modifier: Modifier,
    painter: ImagePainter = rememberImagePainter(data = url)
) {
    val s = rememberCoroutineScope()
    val x = remember { mutableStateOf<Bitmap?>(null) }

/*
    LaunchedEffect(key1 = url){
        s.launch() {
            x.value = painter.imageLoader.execute(painter.request).drawable?.toBitmap(200, 200)
        }
    }
*/


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {

     //   x.value?.let { Image(bitmap = it.asImageBitmap(), contentDescription = "") }

        Image(
            modifier = modifier,
            painter = painter,
            contentDescription = "",
        )
        when (painter.state) {
            is ImagePainter.State.Loading -> {
                // Display a circular progress indicator whilst loading
                MyCircularProgressIndicator()
            }
            is ImagePainter.State.Error -> {
                // If you wish to display some content if the request fails
            }
            ImagePainter.State.Empty -> {}
            is ImagePainter.State.Success -> {}
        }
    }
}
