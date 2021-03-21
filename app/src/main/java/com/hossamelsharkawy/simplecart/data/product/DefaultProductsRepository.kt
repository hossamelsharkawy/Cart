package com.hossamelsharkawy.simplecart.data.product

import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.domain.IProductsDataSource
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject

class DefaultProductsRepository @Inject constructor(
    private val productsRemoteDataSource: IProductsDataSource,
    private val productsLocalDataSource: IProductsDataSource,
    // private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IProductsRepository {

    private var cachedProducts: ConcurrentMap<Int, Product>? = null

    override suspend fun getProducts(forceUpdate: Boolean): Products? {
        return fetchTasksFromRemoteOrLocal()
    }


    private suspend fun fetchTasksFromRemoteOrLocal(): Products? {
        return productsRemoteDataSource
            .getProducts()
            ?.run {
                refreshLocalDataSource(this)
                this
            }
            ?: cachedProducts?.values?.toList()
            ?: productsLocalDataSource.getProducts()
    }


    private suspend fun refreshLocalDataSource(products: Products) {
        cachedProducts?.clear()
        productsLocalDataSource.deleteAll()
        for (product in products) {
            productsLocalDataSource.save(product)
            product.cacheProduct()
        }
    }


    private fun Product.cacheProduct() {
        // Create if it doesn't exist.
        if (cachedProducts == null) {
            cachedProducts = ConcurrentHashMap()
        }
        cachedProducts?.put(id, this)

    }
}
