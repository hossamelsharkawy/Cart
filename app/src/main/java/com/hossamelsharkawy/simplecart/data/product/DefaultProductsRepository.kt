package com.hossamelsharkawy.simplecart.data.product

import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.domain.IProductsDataSource
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import javax.inject.Inject

class DefaultProductsRepository @Inject constructor(
    private val productsRemoteDataSource: IProductsDataSource,
    private val productsLocalDataSource: IProductsDataSource,
    // private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IProductsRepository {

    private var cachedProducts: Products? = null

    override suspend fun getProducts(forceUpdate: Boolean): Products? {
        return fetchProductsFromRemoteOrLocal()
    }

    private suspend fun fetchProductsFromRemoteOrLocal(): Products? {
        if (cachedProducts == null) {
            cachedProducts = productsRemoteDataSource.getProducts()
        }
        return cachedProducts ?: productsLocalDataSource.getProducts()
    }


}
