package com.hossamelsharkawy.simplecart.app.features.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.Coil
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.app.ui.theme.MyTypography
import com.hossamelsharkawy.simplecart.data.entities.Product
import kotlinx.coroutines.withContext


@Composable
fun ProductDetails(
    item: Product,
    productsEvent: ProductsEvent,
) {

    val qtyInCartState = remember { mutableStateOf(item.qtyInCart) }

    Card(
        elevation = 3.dp,
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image1(item.getLargeImage(), item.getSmallImage())
            Text(
                style = MyTypography.h2,
                text = "${item.title} ",
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                modifier = Modifier
                    //  .heightIn(min = 56.dp, max = 60.dp)
                    .padding(horizontal = 5.dp, vertical = 5.dp)
                // .wrapContentHeight()
            )

            Text(
                modifier = Modifier
                    .heightIn(min = 20.dp),
                text = "$${item.price}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1
            )

            Row(
                Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .wrapContentSize(align = Alignment.BottomCenter),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                if (qtyInCartState.value == 0) {
                    Button(
                        modifier = Modifier
                            // .height(35.dp)
                            // .fillMaxWidth()
                            .padding(top = 5.dp, bottom = 6.dp),
                        onClick = {
                            productsEvent.onAddItemClick(item)
                            qtyInCartState.value = item.qtyInCart
                        }
                    ) {
                        Text("Add To Cart")
                    }
                } else {
                    IconButton(
                        modifier = Modifier.size(60.dp),
                        onClick = {
                            productsEvent.onAddItemClick(item)
                            qtyInCartState.value = item.qtyInCart
                        }) {
                        Icon(
                            Icons.Outlined.Add,
                            "",
                            tint = Color.Green
                        )
                    }
                    Text(
                        text = "${item.qtyInCart}",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp,
                        )
                    )

                    IconButton(
                        modifier = Modifier
                            .then(Modifier.size(60.dp)),
                        onClick = {
                            productsEvent.onRemoveItemClick(item)
                            qtyInCartState.value = item.qtyInCart
                        }) {
                        Icon(
                            Icons.TwoTone.Clear,
                            "contentDescription",
                            tint = Color.Red
                        )
                    }

                }
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
fun Image1(urlString: String, urlString2: String) {

    val urlState = remember { mutableStateOf(urlString2) }

    LaunchedEffect(urlString2) {
        urlState.value = urlString2
    }

    val painter = rememberImagePainter(
        data = urlState.value,
        builder = ImageRequestBuilder1
    )


    val scope = rememberCoroutineScope()

    /*LaunchedEffect(urlString2) {
        urlState.value = urlString2
        scope.launch {
            delay(500)
            urlState.value = urlString
        }
    }*/

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

@Composable
private fun xxxx(item: Product) {

    val painter2 = rememberImagePainter(item.getLargeImage())

    val state = painter2.state
/*
    if (state is ImagePainter.State.Error) {

    }*/


    val localImagePainterUrl = remember { mutableStateOf<String?>(null) }

    localImagePainterUrl.value = item.getLargeImage()

    //  val localImagePainterUrl = remember { mutableStateOf<String?>(null) }
    //  localImagePainterUrl.value = item.getLargeImage()
//https://stackoverflow.com/questions/68727567/how-to-set-an-image-url-as-error-placeholder-on-coil-in-jetpack-compose

    val painter = rememberImagePainter(

        data = item.getLargeImage(),
        builder = {
            //transformations(CircleCropTransformation())
            placeholder(drawableResId = R.drawable.ic_menu_gallery)
        }
    )
    val isError = painter.state is ImagePainter.State.Error

    LaunchedEffect(isError) {
        if (isError) {
            localImagePainterUrl.value = item.getSmallImage()
        }
    }
}
