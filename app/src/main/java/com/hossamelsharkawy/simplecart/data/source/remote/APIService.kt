package com.hossamelsharkawy.simplecart.data.source.remote

import com.hossamelsharkawy.simplecart.data.entities.Products
import retrofit2.http.GET


interface APIService {
    companion object {
        const val name = "products/ProductList.json"
    }
    @GET(name)
    suspend fun getList(): Products?
}