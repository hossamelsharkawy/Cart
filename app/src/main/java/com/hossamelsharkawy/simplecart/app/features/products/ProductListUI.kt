package com.hossamelsharkawy.simplecart.app.features.products

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.hossamelsharkawy.simplecart.R
import com.hossamelsharkawy.simplecart.app.ui.theme.MyTypography
import com.hossamelsharkawy.simplecart.data.entities.Category
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products
import java.math.RoundingMode

interface ProductsEvent {
    fun onAddItemClick(product: Product)
    fun onRemoveItemClick(product: Product)
    fun onProductInfoClick(product: Product)
}


fun ProductViewModel1.createProductEvent() = object : ProductsEvent {
    override fun onAddItemClick(product: Product) {
        plusQty(product)
    }

    override fun onRemoveItemClick(product: Product) {
        minQty(product)
    }

    override fun onProductInfoClick(product: Product) {
        openProductInfo(product)
    }
}

@ExperimentalFoundationApi
@Preview(
    backgroundColor = 0xFFDADADA,
    showBackground = true,
    showSystemUi = true,
    // widthDp = 320,
    // heightDp = 1920,
    device = Devices.PIXEL_4_XL
)
@Composable
fun ProductListUI(
    products: Products = fakeProducts,
    productsEvents: ProductsEvent = fakeCartEvent,
    listState: LazyGridState = rememberLazyGridState()
) {
    val minRowWidthInDP = 129 // dp
    val productsMap = products.groupBy { it.category }
    val spanCont = LocalConfiguration.current.screenWidthDp / minRowWidthInDP
    var selected by remember { mutableStateOf<Category?>(null) }

    selected?.let {
        selected = null
    }

    LazyVerticalGrid(
        cells = GridCells.Fixed(spanCont),
        state = listState,
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(
            start = 5.dp,
            top = 5.dp,
            end = 5.dp,
            bottom = 5.dp
        )
    ) {
        productsMap.keys.toList().forEachIndexed { index, category ->
            val productsByCategory = productsMap[category] ?: arrayListOf()
            category(spanCont, index, category, productsByCategory) {
                category.maxView = !category.maxView
                selected = category
            }
            categoryItems(
                spanCont,
                productsByCategory,
                category,
                productsEvents
            )
        }
    }
}

@ExperimentalFoundationApi
fun LazyGridScope.category(
    spanCont: Int,
    index: Int,
    category: Category,
    products: List<Product>,
    onClick: () -> Unit
) {
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(spanCont) }

    item(span = span) {
        Text(
            text = "${category.id}  ${products.size} items",
            style = MyTypography.h3,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = if (index == 0) 0.dp else 15.dp)
                .clickable { onClick() }
        )
    }
}

@ExperimentalFoundationApi
fun LazyGridScope.categoryItems(
    spanCont: Int,
    products: List<Product>,
    category: Category,
    productsEvents: ProductsEvent
) {
    products.forEachIndexed { index, product ->
        item {
            productItem(product, productsEvents, category.maxView)
        }
        if ((index == products.size.minus(1))
        // .and(products.size % spanCont != 0)
        ) {
            repeat(getRemainderRowsNumber(products.size, spanCont)) {
                item {
                    Spacer(Modifier.padding())
                }
            }
        }
    }
}

@Composable
fun productItem(
    item: Product,
    productsEvent: ProductsEvent,
    maxView: Boolean
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier.padding(1.5.dp)
        //.wrapContentSize()
        // .height(194.dp)
        // .fillMaxHeight()
        //.aspectRatio(0.7f)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            productInfo(item, productsEvent, maxView)
            if (maxView) {
                productItemQty(item, productsEvent)
            }
        }
    }
}

val ImageRequestBuilder: ImageRequest.Builder.() -> Unit = {
    //size(150, 150)
    scale(Scale.FIT)
    memoryCachePolicy(CachePolicy.ENABLED)
    networkCachePolicy(CachePolicy.ENABLED)
    //crossfade(500)
    //crossfade(true)
    placeholder(drawableResId = R.drawable.ic_menu_gallery)
    error(R.drawable.ic_logoo)
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun productInfo(
    item: Product,
    productsEvent: ProductsEvent,
    maxView: Boolean
) {
    val size = if (maxView) 80.dp else 50.dp

    Image(
        painter = rememberImagePainter(
            data = item.getSmallImage(),
            builder = ImageRequestBuilder
        ),
        contentDescription = item.title,
        modifier = Modifier
            .height(size)
            .width(size)
            .clickable { productsEvent.onProductInfoClick(item) },
        contentScale = ContentScale.Crop,
    )

    Text(
        style = MyTypography.h1,
        text = "${item.title} ",
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .padding(bottom = 2.dp)
    )

    if (maxView) {
        Text(
            text = item.unit.string(),
            textAlign = TextAlign.Center,
            style = MyTypography.subtitle2
        )

        Text(
            text = "$${item.price}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1
        )
    }
}


@Composable
fun productItemQty(
    item: Product,
    productsEvent: ProductsEvent
) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentSize(align = BottomCenter),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        if (item.qtyInCart == 0) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                onClick = {
                    productsEvent.onAddItemClick(item)
                }
            ) {
                Text("Add To Cart")
            }
        } else {
            IconButton(
                modifier = Modifier.size(width = 60.dp, height = 48.dp),
                onClick = {
                    productsEvent.onAddItemClick(item)
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
                modifier = Modifier.size(width = 60.dp, height = 48.dp),
                onClick = {
                    productsEvent.onRemoveItemClick(item)
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

fun getRemainderRowsNumber(itemsSize: Int, rowCount: Int): Int {


    if (itemsSize < rowCount) return rowCount.minus(itemsSize)
    if (itemsSize.rem(rowCount) == 0) return rowCount

    return itemsSize
        .div(rowCount.toFloat())
        .toBigDecimal()
        .setScale(2, RoundingMode.DOWN)
        .remainder(1.toBigDecimal())
        .minus(1.toBigDecimal())
        .abs()
        .multiply(rowCount.toBigDecimal())
        .toInt()
}

