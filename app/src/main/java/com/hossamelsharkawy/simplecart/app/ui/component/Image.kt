package com.hossamelsharkawy.simplecart.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.hossamelsharkawy.simplecart.R

val ImageRequestBuilder: ImageRequest.Builder.() -> Unit = {
    size(150, 150)
    scale(Scale.FIT)
    diskCachePolicy(CachePolicy.ENABLED)
    memoryCachePolicy(CachePolicy.ENABLED)
    networkCachePolicy(CachePolicy.ENABLED)

    //crossfade(500)
    //crossfade(true)
    placeholder(drawableResId = R.drawable.ic_menu_gallery)
    // error(R.drawable.ic_logoo)
}

@Composable
fun MyImage(url: String, modifier: Modifier) {
    /* https://medium.com/mobile-app-development-publication/speed-up-mandelbrot-drawing-on-android-jetpack-compose-59e90b7f352b

     https://stackoverflow.com/questions/69064634/jetpack-compose-performance-problem-when-element-with-glideimage-is-clickable-i


     https://github.com/CuriousNikhil/neumorphic-compose
     .

     https://github.com/CuriousNikhil/compose-particle-system*/
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = ImageRequestBuilder
        ),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )

}

@Composable
fun MyImageCircle(url: String, modifier: Modifier) {
    MyImage(
        url,
        modifier = Modifier
            .clip(CircleShape) // clip to the circle shape
            // .border(1.dp, Color.Gray, CircleShape)//optional
            .then(modifier)
    )
}
