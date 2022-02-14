package com.hossamelsharkawy.simplecart.app.features.products

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.hossamelsharkawy.simplecart.app.features.cart.MyIconButtonAdd
import com.hossamelsharkawy.simplecart.app.features.cart.MyIconButtonMin
import com.hossamelsharkawy.simplecart.app.features.products.ProductsMapper.getRemainderRowsNumber
import com.hossamelsharkawy.simplecart.app.ui.*
import com.hossamelsharkawy.simplecart.app.ui.component.MyCard
import com.hossamelsharkawy.simplecart.app.ui.component.MyColumnCenter
import com.hossamelsharkawy.simplecart.app.ui.component.MyRowCenter
import com.hossamelsharkawy.simplecart.app.ui.component.MyRowStart
import com.hossamelsharkawy.simplecart.app.ui.theme.*
import com.hossamelsharkawy.simplecart.data.entities.Category
import com.hossamelsharkawy.simplecart.data.entities.Product


interface ProductsEvent {
    fun onAddItemClick(product: Product)
    fun onRemoveItemClick(product: Product)
    fun onProductInfoClick(product: Product)
    fun onSelectQtyClick(qty: Int, product: Product)
}


fun ProductViewModel.createProductEvent() = object : ProductsEvent {
    override fun onAddItemClick(product: Product) {
        plusQty(product)
    }

    override fun onRemoveItemClick(product: Product) {
        minQty(product)
    }

    override fun onProductInfoClick(product: Product) {
        openProductInfo(product)
    }

    override fun onSelectQtyClick(qty: Int, product: Product) {
        setQty(qty, product)
    }
}

/*@ExperimentalFoundationApi
@Preview(
    backgroundColor = 0xFFDADADA,
    showBackground = true,
    showSystemUi = true,
    // widthDp = 320,
    // heightDp = 1920,
    device = Devices.PIXEL_4_XL,
)*/


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductListUI(
    productsEvents: ProductsEvent = fakeCartEvent,
    productsMapState: Map<Category, List<Product>>,
    spanCont: Int,
    listState: LazyGridState = rememberLazyGridState(),
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(spanCont),
        state = listState,
        horizontalArrangement = Arrangement.Center,
    ) {
        productsMapState
            .entries
            .sortedBy { it.key.index }
            .onEach { entry ->
                val category = entry.key
                val items = entry.value

                categoryGirdItem(spanCont, category) {
                    entry.key.toggleMaxView()
                }

                categoryItems(spanCont, items, category = category, productsEvents)
            }
    }
}


@ExperimentalFoundationApi
fun LazyGridScope.categoryGirdItem(
    spanCont: Int,
    // index: Int,
    category: Category,
    onClick: () -> Unit
) {
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(spanCont) }

    item(
        span = span,
        key = category.id
    ) {

        val maxViewState = remember { category.maxViewState }

        val animatedFloatState = animateFloatAsState(
            targetValue = if (maxViewState.value) 0.0f else 90f,
            animationSpec = tween(durationMillis = 500)
        )

        MyRowStart(Modifier.fillMaxWidth().padding(start = 15.dp  , bottom = 1.dp , top = 10.dp).clickable { onClick.invoke()}) {
            MyTitleCenter2(text = category.id)
            MyIconButtonSmall(
                imageVector = Icons.Default.KeyboardArrowDown,
                tint = MyColor.BottomNavigationBackgroundColor,
                modifier = Modifier.rotate(animatedFloatState.value)
            ) {
                onClick.invoke()
            }
        }

    }
}


@ExperimentalFoundationApi
fun LazyGridScope.categoryItems(
    spanCont: Int,
    products: List<Product>,
    category: Category,
    productsEvents: ProductsEvent
) {
    products
        .forEachIndexed { index, product ->
            item(key = product.id) {
                ProductItem(product, productsEvents)
            }
            if ((index == products.size.minus(1))
            // .and(products.size % spanCont != 0)
            ) {
                repeat(getRemainderRowsNumber(products.size, spanCont)) {
                    item {
                        key(arrayOf(it, product.id)) {
                            Spacer(Modifier.padding())
                        }
                    }
                }
            }
        }
}

@Composable
fun ProductItem(
    item: Product,
    productsEvent: ProductsEvent,
    maxView: Boolean = item.category.maxViewState.value
) {
    val modifier = Modifier.clickable { productsEvent.onProductInfoClick(item) }
    MyCard {
        MyColumnCenter {
            productInfo(modifier ,item, maxView)
            AnimatedVisibility(visible = maxView) {
                MyColumnCenter {
                    MyRowCenter(modifier) {
                        MyPrice(text = item.priceString())
                        MySubtitle2(text = item.unit.string(), Modifier.padding(start = 5.dp))
                    }
                    productItemQty(item, productsEvent)
                }

            }
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun productInfo(
    modifier: Modifier = Modifier,
    item: Product,
    maxView: Boolean
) {
    val size = if (maxView) 70.dp else 50.dp
    MyImage(
        url = item.getSmallImage(),
        modifier = Modifier
            //.shadow(0.5.dp, shape = RoundedCornerShape(50))
            //.clip(RoundedCornerShape(50))
            .height(size)
            .width(size)
            .then(modifier)
    )

    MyTitle(
        text = "${item.title} ",
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .padding(bottom = 3.dp)
            .then(modifier)
    )

}


@Composable
fun productItemQty(
    item: Product,
    productsEvent: ProductsEvent
) {
    MyRowStart {
        MyIconButtonAdd {
            productsEvent.onAddItemClick(item)
        }
        if (item.qtyInCart != 0) {
            MyNumberCenter(
                text = item.qtyInCart.toString(),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            MyIconButtonMin {
                productsEvent.onRemoveItemClick(item)
            }
        }
    }
}

/*******************************/

sealed class GridItem<T>(val data: T) {
    class Spacer(id: String) : GridItem<String>(id)
    class CategoryItem(category: Category) : GridItem<Category>(category)
    class ProductItem(product: Product) : GridItem<Product>(product)
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.spacerGridItem(key: String) {
    item(key) {
        Spacer(Modifier.padding())
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.productGirdItem(product: Product, productsEvents: ProductsEvent) {
    item(key = product.id) {
        ProductItem(product, productsEvents)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductsListGridItems(
    gridItems: List<GridItem<*>>,
    productsEvents: ProductsEvent = fakeCartEvent,
    spanCont: Int,
    listState: LazyGridState = rememberLazyGridState(),
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(spanCont),
        state = listState,
        horizontalArrangement = Arrangement.Center
    ) {
        gridItems.forEach { gridItem ->
            when (gridItem) {
                is GridItem.CategoryItem -> {
                    val category = gridItem.data
                    categoryGirdItem(spanCont, category) {
                        category.toggleMaxView()
                    }
                }
                is GridItem.ProductItem -> productGirdItem(gridItem.data, productsEvents)
                is GridItem.Spacer -> spacerGridItem(gridItem.data)
            }
        }
    }
}




