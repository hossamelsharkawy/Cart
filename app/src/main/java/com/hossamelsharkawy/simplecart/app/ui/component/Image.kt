package com.hossamelsharkawy.simplecart.app.ui.component

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.transition.Transition
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.app.ui.theme.MyColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.hossamelsharkawy.base.extension.launchOnUI
import kotlinx.coroutines.*
import java.util.concurrent.Executors


val ImageRequestBuilder: ImageRequest.Builder.() -> Unit = {
    size(150, 150)
    scale(Scale.FIT)
    diskCachePolicy(CachePolicy.DISABLED)
    memoryCachePolicy(CachePolicy.DISABLED)
    networkCachePolicy(CachePolicy.ENABLED)

    // crossfade(200)
    //crossfade(true)
    // placeholder(drawableResId = R.drawable.ic_menu_gallery)
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



@Composable
fun MyImageLoading(url: String, modifier: Modifier){
   // MyImageLoadingGlide(url, modifier)
   MyImageLoadingPicasso(url, modifier)
    //MyImageLoadingCoil(url, modifier)
}

@Composable
fun MyCircularProgressIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier,
        strokeWidth = 2.dp,
        color = MyColor.GreenLite
    )
}


@Composable
fun MyLinearProgressIndicator(modifier: Modifier = Modifier) {
    LinearProgressIndicator(
        modifier,
        color = MyColor.GreenLite
    )
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun MyImageWithTemp(url: String, urlTemp: String, modifier: Modifier = Modifier) {

    val painter = rememberImagePainter(
        data = url,
    )

    Box(contentAlignment = Alignment.Center) {
        Image(
            modifier = modifier,
            painter = painter,
            contentDescription = "",
        )
        when (painter.state) {
            is ImagePainter.State.Loading -> {
                Image(
                    modifier = modifier,
                    painter = rememberImagePainter(
                        data = urlTemp,
                    ),
                    contentDescription = "",
                )


                MyCircularProgressIndicator(
                    modifier = Modifier
                        .size(300.dp)
                        .align(Alignment.Center)
                )

            }
            is ImagePainter.State.Error -> {

            }
            ImagePainter.State.Empty -> {

            }
            is ImagePainter.State.Success -> {


            }
        }
    }
}


val ImageRequestBuilder1: ImageRequest.Builder.() -> Unit = {
    // size(700, 700)
    scale(Scale.FILL)
    memoryCachePolicy(CachePolicy.ENABLED)
    networkCachePolicy(CachePolicy.ENABLED)
    //crossfade(500)
    //crossfade(true)
    //  placeholder(drawableResId = R.drawable.ic_menu_gallery)
    // error(R.drawable.ic_logoo)
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun Image2(urlString: String, urlString2: String) {

    val urlState = remember { mutableStateOf(urlString2) }

    LaunchedEffect(urlString2) {
        urlState.value = urlString2
    }

    val painter = rememberImagePainter(
        data = urlState.value,
        builder = ImageRequestBuilder1
    )


    val scope = rememberCoroutineScope()


    val context = LocalContext.current
    LaunchedEffect(urlString2) {
        val x = withContext(scope.coroutineContext) {
            val imageResult = Coil
                .execute(
                    request = ImageRequest.Builder(context)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .networkCachePolicy(CachePolicy.ENABLED)
                        .listener(onSuccess = { _, _ ->
                            urlState.value = urlString
                        })
                        .data(urlString)
                        .build()
                )
            imageResult.request
        }

        //  urlState.value = x.data.toString()

        if (painter.state is ImagePainter.State.Error) {
            urlState.value = urlString2
        }


    }

    Column {
        Image(
            modifier = Modifier
                //.fillMaxSize()
                .size(300.dp),
            painter = painter,
            contentDescription = null,
            //    contentScale = ContentScale.Fit
        )
        // Text(text = painter.state::class.java.simpleName)
    }

}
