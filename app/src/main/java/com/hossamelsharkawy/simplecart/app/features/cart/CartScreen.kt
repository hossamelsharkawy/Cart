package com.hossamelsharkawy.simplecart.app.features.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.hossamelsharkawy.simplecart.app.features.products.ProductViewModel
import com.hossamelsharkawy.simplecart.app.features.products.ProductsEvent
import com.hossamelsharkawy.simplecart.app.features.products.fakeCartEvent
import com.hossamelsharkawy.simplecart.app.features.products.fakeProduct
import com.hossamelsharkawy.simplecart.app.ui.*
import com.hossamelsharkawy.simplecart.app.ui.theme.MyColor
import com.hossamelsharkawy.simplecart.app.ui.theme.MyTypography
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products




@Preview(device = Devices.NEXUS_10)
@Composable
fun cartP() {
    //   CartUI(cartCount = 5, products = fakeProducts, cartEvent = fakeCartEvent)
}


@Composable
fun CartScreen(
    cart: CartStates,
    cartEvent: CartEvent
) {
    val productsSnap = remember { cart.cartItemsState }

    Column(
        Modifier
            .fillMaxWidth()
        //  .padding(top = 5.dp, start = 10.dp, end = 10.dp)
    ) {

        CartItemsListUI(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 5.dp)
                .fillMaxWidth(),
            productsSnap, cartEvent
        )

        val modifier = Modifier.padding(start = 20.dp)

        Text(
            modifier = modifier,
            text = "Sub Price :${cart.subPriceState.value} LE",
            style = MyTypography.subtitle1
        )

        Text(
            modifier = modifier,

            text = "Delivery Fee :${cart.deliveryFeeState.value} LE",
            style = MyTypography.subtitle1
        )

        /*Text(
            text = "Total Price :${cart.totalPriceState.value} LE",
            style = MyTypography.h3
        )*/

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CheckOut(cartEvent::onCheckOut, cart.totalPriceState.value)
        }
    }
}


@Composable
fun CheckOut(onCheckOutClick: () -> Unit, totalPrice: Double) {
    Button(
        onCheckOutClick,
        Modifier.padding(5.dp)
    ) {
        Text(text = "Checkout  >> $totalPrice LE", style = MyTypography.h1)
    }
}

@Composable
fun CartItemsListUI(
    modifier: Modifier,
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
        modifier = modifier.fillMaxWidth(),
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
    itemInfo(item, event)
    /*Card(
        elevation = 1.5.dp,
        modifier = Modifier.padding(bottom = 1.9.dp)
    ) {
        itemInfo(item, event)
    }*/
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun itemInfo(
    item: Product,
    event: CartEvent
) {
    Row(
        Modifier
            .background(MyColor.white)
            //  .border(color = Color.Gray , width = 0.1.dp)
            // .fillMaxWidth()
            .clickable { event.onProductInfoClick(item) }
            .padding(start = 5.dp, end = 5.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {

        MyImage(
            item.getSmallImage(),
            modifier = Modifier
                .padding(start = 10.dp)
                .height(50.dp)
                .width(50.dp),
        )
        Column(
            Modifier
                .weight(1f)
                .padding(start = 20.dp)

        ) {
            MyTitle(
                text = "${item.title}",
                modifier = Modifier.wrapContentHeight()
            )
            MyPrice(
                text = "${item.cartPrice}"
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
        Modifier.wrapContentWidth(CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,

        ) {

        IconButton(
            modifier = Modifier.size(50.dp),
            onClick = {
                event.onAddItemClick(item)
            }) {
            Icon(
                Icons.Outlined.Add,
                "",
                tint = MyColor.green
            )
        }

        Text(
            text = "${item.qtyInCart}",
            Modifier.padding(10.dp)
        )

        IconButton(
            modifier = Modifier.size(50.dp),
            onClick = { event.onRemoveItemClick(item) }

        ) {
            Icon(
                Icons.TwoTone.Clear,
                "contentDescription",
                tint = MyColor.red
            )
        }

    }
}



