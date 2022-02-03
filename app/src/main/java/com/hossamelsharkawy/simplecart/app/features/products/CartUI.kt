package com.hossamelsharkawy.simplecart.app.features.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.hossamelsharkawy.simplecart.app.ui.theme.MyTypography
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products


interface CartEvent : ProductsEvent {
    fun onRemoveAll()
    fun onCheckOut()
}


fun ProductViewModel1.createCartEvent() = object : CartEvent {

    override fun onRemoveAll() {
        clearCart()
    }

    override fun onCheckOut() {

    }

    override fun onAddItemClick(product: Product) {
        addToCart(product)
    }

    override fun onRemoveItemClick(product: Product) {
        minQty(product)
    }

    override fun onProductInfoClick(product: Product) {
        openProductInfo(product)
    }
}


@Composable
fun CartUI(
    cartCount: Int,
    products: Products,
    cartEvent: CartEvent
) {
    val productsSnap = remember { products }

    Column {
        Text(
            text = "TotalCount:${cartCount}",
            Modifier.padding(5.dp),
            style = MyTypography.h3
        )

        CartItemsListUI(productsSnap, cartEvent)

    }
}

@Composable
fun CartItemsListUI(
    products: Products,
    cartEvent: CartEvent
) {
    val listState = rememberLazyListState()

    //  var  state: LazyListState? = rememberLazyListState()
    /* var selected by remember { mutableStateOf<Product?>(null) }
     selected?.let {
         selected = null
     }*/

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(
            key = Product::id,
            items = products,
        ) { item -> CartItemUI(item, cartEvent) }
    }
}

@Composable
fun CartItemUI(
    item: Product = fakeProduct,
    event: CartEvent = fakeCartEvent
) {
    Card(
        elevation = 1.5.dp,
        modifier = Modifier.padding(1.9.dp)
    ) {
        itemInfo(item, event)
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun itemInfo(
    item: Product,
    event: CartEvent
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {

        Image(
            painter = rememberImagePainter(
                data = item.getSmallImage(),
                builder = ImageRequestBuilder
            ),
            contentDescription = item.title,
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .clickable { event.onProductInfoClick(item) },
            contentScale = ContentScale.Fit,
        )
        Column {
            Text(
                text = "${item.title} ",
                overflow = TextOverflow.Ellipsis,
                style = MyTypography.h2,
                // color = JetsnackTheme.colors.brand,
                modifier = Modifier
                    .heightIn(min = 56.dp)
                    .padding(horizontal = 5.dp, vertical = 5.dp)
                    .wrapContentHeight()
                    .fillMaxWidth(.7f)
            )
            Text(
                text = "$${item.cartPrice}  ",
                color = Color.Gray
            )
        }
        productItemQty(item, event)
    }
}


@Composable
fun productItemQty(
    item: Product,
    event: CartEvent
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,

        ) {

        IconButton(
            modifier = Modifier
                .then(Modifier.size(40.dp)),
            onClick = {
                event.onAddItemClick(item)
            }) {
            Icon(
                Icons.Outlined.Add,
                "",
                tint = Color.Green
            )
        }


        Text(
            text = "${item.qtyInCart}",
            Modifier.padding(10.dp)
        )

        IconButton(
            modifier = Modifier.then(Modifier.size(40.dp)),
            onClick = { event.onRemoveItemClick(item) }

        ) {
            Icon(
                Icons.TwoTone.Clear,
                "contentDescription",
                tint = Color.Red
            )
        }

    }
}



