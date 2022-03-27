package com.hossamelsharkawy.simplecart.app.ui.component

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.hossamelsharkawy.simplecart.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.Exception


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun MyImageLoadingPicasso(url: String, modifier: Modifier) {

    val imageState = loadPicture(url = url, defaultImage = R.drawable.ic_instacart_logo)

    Picasso.get().load(url).into(object : Target{

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            imageState.value = bitmap
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        }

    })

    imageState.value?.let { img ->
        Image(
            bitmap = img.asImageBitmap(),
            contentDescription = null,
            modifier = modifier,
        )
    }
}