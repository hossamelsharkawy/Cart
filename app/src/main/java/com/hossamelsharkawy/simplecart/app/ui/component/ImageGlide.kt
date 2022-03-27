package com.hossamelsharkawy.simplecart.app.ui.component

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.CustomTarget
import com.hossamelsharkawy.simplecart.R
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun loadPicture(url: String, @DrawableRes defaultImage: Int): MutableState<Bitmap?> {
//Asynchronously Load Images with Jetpack Compose

    val glideUrl = GlideUrl(
        url,
        LazyHeaders.Builder().addHeader(
            "User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.67"
        ).build()
    )


    val context = LocalContext.current


    val bitmapState = remember { mutableStateOf<Bitmap?>(null) }


    // show default image while image loads

/*
      Glide.with(context)
          .asBitmap()
          .load(defaultImage)
          .into(object : CustomTarget<Bitmap>() {
              override fun onResourceReady(
                  resource: Bitmap,
                  transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
              ) {
                  // Customize Target, After loading, transfer the picture resources to bitmapState
                  bitmapState.value = resource
              }

              override fun onLoadCleared(placeholder: Drawable?) {}
          })
*/

    // get network image

    try {
        Glide.with(context)
            .asBitmap()
            .load(glideUrl)
            .placeholder(defaultImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    // Customize Target, After loading, transfer the picture resources to bitmapState
                    bitmapState.value = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    } catch (glideException: GlideException) {
        Log.d("TAG", "morsyerror: ${glideException.rootCauses}")
    }

    return bitmapState
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MyImageLoadingGlide(url: String, modifier: Modifier) {
    val imageState = loadPicture(url = url, defaultImage = R.drawable.ic_instacart_logo)


    imageState.value?.let { img ->
        Image(
            bitmap = img.asImageBitmap(),
            contentDescription = null,
            modifier = modifier,
        )
    }
}