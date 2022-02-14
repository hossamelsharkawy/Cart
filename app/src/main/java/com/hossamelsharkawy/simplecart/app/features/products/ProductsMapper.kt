package com.hossamelsharkawy.simplecart.app.features.products

import androidx.compose.runtime.mutableStateListOf
import com.hossamelsharkawy.simplecart.app.util.util.swapList
import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products
import java.math.RoundingMode

object ProductsMapper {

    fun Products.toMapByCategory() =
        groupBy { it.category }
            .mapValues {
                mutableStateListOf<Product>().apply { swapList(it.value) }
            }
            .toMutableMap()

    fun Products.toGridItems(rowCount: Int) =
        groupBy { it.category }
            .flatMap { categoryEntry ->

                arrayListOf<GridItem<*>>()
                    .apply {
                        add(GridItem.CategoryItem(categoryEntry.key))
                        categoryEntry.value
                            .forEach { product ->
                                add(GridItem.ProductItem(product))
                            }

                        repeat(
                            getRemainderRowsNumber(
                                categoryEntry.value.size,
                                rowCount
                            )
                        ) { index ->
                            add(GridItem.Spacer("${categoryEntry.value.last().id} $index"))
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
}
