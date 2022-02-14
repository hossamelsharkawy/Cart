package com.hossamelsharkawy.simplecart.app.features.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.app.features.cart.MyIconButtonAdd
import com.hossamelsharkawy.simplecart.app.features.cart.MyIconButtonMin
import com.hossamelsharkawy.simplecart.app.features.cart.SelectQtyUI
import com.hossamelsharkawy.simplecart.app.ui.MyNumberCenter
import com.hossamelsharkawy.simplecart.app.ui.MySubtitle
import com.hossamelsharkawy.simplecart.app.ui.MyTitle
import com.hossamelsharkawy.simplecart.app.ui.component.MyCard
import com.hossamelsharkawy.simplecart.app.ui.component.MyColumnCenter
import com.hossamelsharkawy.simplecart.app.ui.component.MyRowCenter
import com.hossamelsharkawy.simplecart.data.entities.Product
import kotlinx.coroutines.withContext


@Composable
fun ProductDetails(
    item: Product,
    productsEvent: ProductsEvent,
) {

    val qtyInCartState = remember { mutableStateOf(item.qtyInCart) }

    fun updateQtyInCart() {
        qtyInCartState.value = item.qtyInCart
    }

    LaunchedEffect(item.qtyInCart) {
        updateQtyInCart()
    }

    MyCard {
        MyColumnCenter {
            Image1(item.getLargeImage(), item.getSmallImage())

            MyTitle(
                text = item.title,
                maxLines = 3,
                modifier = Modifier
                    .padding(5.dp)
            )

            MySubtitle(
                modifier = Modifier
                    .heightIn(min = 20.dp),
                text = "$${item.price}",
                textAlign = TextAlign.Center,
            )

            MyRowCenter {
                MyIconButtonAdd {
                    productsEvent.onAddItemClick(item)
                    qtyInCartState.value = item.qtyInCart
                }
                MyNumberCenter(
                    text = qtyInCartState.value,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )

                MyIconButtonMin {
                    productsEvent.onRemoveItemClick(item)
                    qtyInCartState.value = item.qtyInCart
                }


            }

            SelectQtyUI { qty ->
                productsEvent.onSelectQtyClick(qty, item)
                qtyInCartState.value = item.qtyInCart
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

