package com.hossamelsharkawy.simplecart.data.product

import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.data.source.remote.APIService
import com.hossamelsharkawy.simplecart.domain.IProductsDataSource
import javax.inject.Inject

class ProductsRemoteDataSource @Inject constructor(
    private val apiService: APIService
) : IProductsDataSource {

    override suspend fun getProducts(): Products? {
        return try {
            apiService.getList()
        } catch (e: Exception) {
            null
        }
    }

}





