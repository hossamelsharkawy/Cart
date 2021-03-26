package com.hossamelsharkawy.simplecart.app.di

import android.content.Context
import com.hossamelsharkawy.simplecart.data.cart.CartLocalDataSource
import com.hossamelsharkawy.simplecart.data.cart.DefaultCartRepository
import com.hossamelsharkawy.simplecart.data.product.DefaultProductsRepository
import com.hossamelsharkawy.simplecart.data.product.ProductsLocalDataSource
import com.hossamelsharkawy.simplecart.data.product.ProductsRemoteDataSource
import com.hossamelsharkawy.simplecart.data.source.remote.APIService
import com.hossamelsharkawy.simplecart.domain.ICartDataSource
import com.hossamelsharkawy.simplecart.domain.ICartRepository
import com.hossamelsharkawy.simplecart.domain.IProductsDataSource
import com.hossamelsharkawy.simplecart.domain.IProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDefaultProductRepository(
        @RemoteProductDataSource remote: IProductsDataSource,
        @LocalProductDataSource local: IProductsDataSource
    ) = DefaultProductsRepository(remote, local) as IProductsRepository


    @RemoteProductDataSource
    @Singleton
    @Provides
    fun provideRemoteProductsDataSource(
        api: APIService
    ): IProductsDataSource = ProductsRemoteDataSource(api)

    @LocalProductDataSource
    @Singleton
    @Provides
    fun provideLocalProductDataSource(
    ): IProductsDataSource = ProductsLocalDataSource()


    @Singleton
    @Provides
    fun provideDefaultCartRepository(
        cartDataStore: ICartDataSource
    ) :ICartRepository = DefaultCartRepository(cartDataStore)

    @Singleton
    @Provides
    fun provideCartDataStore(@ApplicationContext appContext: Context):
            ICartDataSource = CartLocalDataSource(appContext)

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteProductDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalProductDataSource


