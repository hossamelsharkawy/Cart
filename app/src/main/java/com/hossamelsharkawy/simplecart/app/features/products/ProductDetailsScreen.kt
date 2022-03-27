package com.hossamelsharkawy.simplecart.app.features.products

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hossamelsharkawy.simplecart.app.features.cart.MyIconButtonAdd
import com.hossamelsharkawy.simplecart.app.features.cart.MyIconButtonMin
import com.hossamelsharkawy.simplecart.app.features.cart.SelectQtyUI
import com.hossamelsharkawy.simplecart.app.ui.component.MyNumberCenter
import com.hossamelsharkawy.simplecart.app.ui.component.MySubtitle
import com.hossamelsharkawy.simplecart.app.ui.component.MyTitle
import com.hossamelsharkawy.simplecart.app.ui.component.MyCard
import com.hossamelsharkawy.simplecart.app.ui.component.MyColumnCenter
import com.hossamelsharkawy.simplecart.app.ui.component.MyImageWithTemp
import com.hossamelsharkawy.simplecart.app.ui.component.MyRowCenter
import com.hossamelsharkawy.simplecart.data.entities.Product


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
            MyImageWithTemp(item.getLargeImage(), item.getSmallImage(), Modifier.size(300.dp))

            MyTitle(
                text = item.title,
                maxLines = 3,
                modifier = Modifier.padding(5.dp)
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

