package com.hossamelsharkawy.simplecart.app.di

import com.hossamelsharkawy.simplecart.app.UIRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun uIRoute() = UIRouter()

}


