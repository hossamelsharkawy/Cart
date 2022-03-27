package com.hossamelsharkawy.simplecart.app.features.products

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import coil.annotation.ExperimentalCoilApi
import com.hossamelsharkawy.simplecart.app.features.cart.MyIconButtonAdd
import com.hossamelsharkawy.simplecart.app.features.cart.MyIconButtonMin
import com.hossamelsharkawy.simplecart.app.features.products.ProductsMapper.getRemainderRowsNumber
import com.hossamelsharkawy.simplecart.app.ui.component.*
import com.hossamelsharkawy.simplecart.app.ui.theme.MyColor
import com.hossamelsharkawy.simplecart.data.entities.Category
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.data.source.local.fakeCartEvent
import com.hossamelsharkawy.simplecart.data.source.local.fakeProduct


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
fun ProductListScreen(
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

                categoryItems(spanCont, items, productsEvents)
            }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductListSimpleScreen(
    productsEvents: ProductsEvent = fakeCartEvent,
    products: Products,
    listState: LazyGridState = rememberLazyGridState(),
) {

    //val modifierPadding5 = Modifier.padding(start = 5.dp)

    LazyVerticalGrid(
        cells = GridCells.Adaptive(130.dp),
        state = listState,
        horizontalArrangement = Arrangement.Center,
    ) {
        items(
            key = { it.id },
            items = products
        ) { item ->
            ItemConstraintLayout(productsEvents, item)
        }
    }
}

val modifierImage = Modifier.size(70.dp)


@Preview(widthDp = 320)
@Composable
fun ItemConstraintLayout(
    productsEvents: ProductsEvent = fakeCartEvent,
    item: Product = fakeProduct
) {
    MyCard {
        ConstraintLayout(
            modifier = Modifier.padding(5.dp),
        ) {

            val (
                Image,
                MyTitle,
                MyPrice,
                MySubtitle2,
                MyIconButtonAdd,
                MyNumberCenter,
                MyIconButtonMin
            ) = createRefs()


            MyImageLoading(
                url = item.getSmallImage(),
                modifier = modifierImage.then(
                    Modifier.constrainAs(Image) {
                        top.linkTo(parent.top, margin = 5.dp)
                    })
            )

            MyTitle(
                text = "${item.title} ",
                modifier = Modifier.constrainAs(MyTitle)
                {
                    top.linkTo(Image.bottom, margin = 5.dp)
                    end.linkTo(MyIconButtonMin.start, margin = 5.dp)
                    start.linkTo(parent.start, margin = 5.dp)
                    bottom.linkTo(MyPrice.top)
                    width = Dimension.fillToConstraints
                }
            )

            MyPrice(item.priceString(), Modifier.constrainAs(MyPrice) {
                top.linkTo(MyTitle.bottom)
                start.linkTo(parent.start, margin = 5.dp)
            })

            MySubtitle2(item.unit.string(), MySubtitle2.end(MyPrice, this))

            this(MyPrice ,MySubtitle2)

            MyIconButtonAdd(Modifier.constrainAs(MyIconButtonAdd) {
                end.linkTo(parent.end, margin = (-20).dp)
                bottom.linkTo(parent.bottom)
            }) {
                productsEvents.onAddItemClick(
                    item
                )
            }
            MyNumberCenter(
                Modifier.constrainAs(MyNumberCenter) {
                    bottom.linkTo(MyIconButtonAdd.top)
                    end.linkTo(MyIconButtonAdd.end)
                    start.linkTo(MyIconButtonAdd.start)
                },
                text = item.qtyInCart.toString(),
            )
            MyIconButtonMin(Modifier.constrainAs(MyIconButtonMin) {
                bottom.linkTo(MyNumberCenter.top)
                end.linkTo(MyNumberCenter.end)
                start.linkTo(MyNumberCenter.start)

            }) {
                productsEvents.onRemoveItemClick(
                    item
                )
            }
        }
    }
}

private operator fun ConstraintLayoutScope.invoke(
    myPrice: ConstrainedLayoutReference,
    mySubtitle2: ConstrainedLayoutReference
) {
    myPrice.end(mySubtitle2, this)
}

private fun ConstrainedLayoutReference.end(
    myPrice: ConstrainedLayoutReference,
    constraintLayoutScope: ConstraintLayoutScope
): Modifier {
    with(constraintLayoutScope) {
        return Modifier.constrainAs(this@end) {
            start.linkTo(myPrice.end, margin = 5.dp)
            top.linkTo(myPrice.top)
        }
    }
}

private fun ConstrainedLayoutReference.start(
    myPrice: ConstrainedLayoutReference,
    constraintLayoutScope: ConstraintLayoutScope
): Modifier {
    with(constraintLayoutScope) {
        return Modifier.constrainAs(this@start) {
            start.linkTo(myPrice.end, margin = 5.dp)
            top.linkTo(myPrice.top)
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

        MyRowStart(
            Modifier
                .fillMaxWidth()
                .padding(start = 7.dp, bottom = 0.dp, top = 10.dp)
                .clickable { onClick.invoke() }) {
            MyTitleCenter4(text = category.id)
            MyIconButtonSmall(
                imageVector = Icons.Default.KeyboardArrowDown,
                tint = MyColor.BlueDark,
                modifier = Modifier
                    .rotate(animatedFloatState.value)
                    .align(CenterVertically)
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
            ProductInfo(modifier, item, maxView)
            AnimatedVisibility(visible = maxView) {
                MyColumnCenter {
                    MyRowCenter(modifier) {
                        MyPrice(text = item.priceString())
                        MySubtitle2(text = item.unit.string(), Modifier.padding(start = 5.dp))
                    }
                    ProductItemQty(item, productsEvent)
                }

            }
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProductInfo(
    modifier: Modifier = Modifier,
    item: Product,
    maxView: Boolean
) {
    val size = if (maxView) 70.dp else 50.dp
    MyImageLoading(
        url = item.getSmallImage(),
        modifier = Modifier
            //.shadow(0.5.dp, shape = RoundedCornerShape(50))
            //.clip(RoundedCornerShape(50))
            .size(size)
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


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProductItemQty(
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
                modifier = Modifier.align(CenterVertically)
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
fun ProductsListGridItemsScreen(
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

