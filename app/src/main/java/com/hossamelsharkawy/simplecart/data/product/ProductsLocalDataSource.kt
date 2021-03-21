package com.hossamelsharkawy.simplecart.data.product

import com.hossamelsharkawy.simplecart.data.entities.Product
import com.hossamelsharkawy.simplecart.data.entities.Products
import com.hossamelsharkawy.simplecart.domain.IProductsDataSource
import javax.inject.Inject

class ProductsLocalDataSource @Inject constructor() : IProductsDataSource {


    override suspend fun getProducts(): Products {
        return arrayListOf<Product>().apply {
            add(
                Product(
                    id = 1,
                    title = "Tanimura & Antle Organic Organic Romaine Hearts",
                    image = "https://d2d8wwwkmhfcva.cloudfront.net/600x/filters:fill(FFF,true):format(jpg)/d2lnr5mha7bycj.cloudfront.net/product-image/file/large_67f0d47a-4bb7-4537-a6a8-86107d4b9af3.jpg",
                    price = "60.00",
                    price_new  = "57.00",
                    has_discount = true
                )
            )
            add(
                Product(
                    id = 2,
                    title = "Tanimura & Antle Organic Organic Romaine Hearts",
                    image = "https://d2d8wwwkmhfcva.cloudfront.net/600x/filters:fill(FFF,true):format(jpg)/d2lnr5mha7bycj.cloudfront.net/product-image/file/large_5d2088d9-0b8e-464c-bc17-abd963f2f161.jpg",
                    price = "12.00",
                    price_new  = "50.00",
                    has_discount = true
                )
            )

            add(
                Product(
                    id = 3,
                    title = "Organic Tomato",
                    image = "https://d2d8wwwkmhfcva.cloudfront.net/600x/filters:fill(FFF,true):format(jpg)/d2lnr5mha7bycj.cloudfront.net/product-image/file/large_cfacb337-14bd-42f7-bb7b-1a8a5c8521a4.jpg",
                    price = "12.00",
                    price_new  = "17.00",
                    has_discount = false
                )
            )

            add(
                Product(
                    id = 4,
                    title = "Organic Bananas",
                    image = "https://d2d8wwwkmhfcva.cloudfront.net/600x/filters:fill(FFF,true):format(jpg)/d2lnr5mha7bycj.cloudfront.net/product-image/file/large_7418ae1d-6990-4e22-9f8e-2b7a18fd0013.jpg",
                    price = "50.00",
                    price_new  = "40.00",
                    has_discount = true
                )
            )

        }
    }

}


