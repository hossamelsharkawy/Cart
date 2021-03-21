package com.hossamelsharkawy.simplecart.domain

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private val lock = Any()

   /* @Volatile
    var productsRepository: ProductsRepository? = null
        @VisibleForTesting set


    fun provideRepository(): ProductsRepository {
        synchronized(this) {
            return productsRepository ?: createRepository()
        }
    }

    private fun createRepository(): ProductsRepository {
        return DefaultProductsRepository(
            ProductsRemoteDataSource,
            ProductsLocalDataSource
        )
    }*/


    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            runBlocking {
                // FakeRemoteDataSource.deleteAllTasks()
            }
            // Clear all data to avoid test pollution.
            /* database?.apply {
                 clearAllTables()
                 close()
             }
             Repository = null*/
        }
    }
}

