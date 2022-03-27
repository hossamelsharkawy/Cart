package com.hossamelsharkawy.simplecart.data.product

import com.hossamelsharkawy.simplecart.data.source.local.fakeProducts2
import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.domain.IProductsDataSource
import javax.inject.Inject

class ProductsLocalDataSource @Inject constructor() : IProductsDataSource {


    override suspend fun getProducts(): Products {
        return fakeProducts2
    }

}


