package com.hossamelsharkawy.simplecart.app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.size.Precision
import coil.util.CoilUtils
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {

    /**
     * Create the singleton [ImageLoader].
     * This is used by [rememberImagePainter] to load images in the app.
     *
     * https://coil-kt.github.io/coil/image_loaders/
     */
    @OptIn(ExperimentalCoilApi::class)
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            //  .availableMemoryPercentage(0.25)
            .dispatcher(Dispatchers.Unconfined)
            .launchInterceptorChainOnMainThread(false)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .precision(precision = Precision.INEXACT)
            //.crossfade(500)
            .okHttpClient {
                OkHttpClient.Builder()
                    .readTimeout(timeout = 1, TimeUnit.MINUTES)
                    .callTimeout(timeout = 1, TimeUnit.MINUTES)
                    .cache(CoilUtils.createDefaultCache(this))
                    .build()
            }
            /*   .componentRegistry {
                  add(UnsplashSizingInterceptor)
                add(SvgDecoder(this@App))
               }*/
            .build()
    }


    /*  @Inject
      lateinit var provideOkHttpClient: OkHttpClient*/

    companion object {
        private lateinit var instance: App

        fun get() = instance

        fun getDrawable(id: Int) = androidx.core.content.ContextCompat.getDrawable(
            instance, id
        )
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //setServer()
        //  setBase()


    }
/*
    private fun setBase() {
        BaseApp.init(
            app = instance,
            client = provideOkHttpClient,
            logo = R.mipmap.ic_launcher,
            onCrashAction = {
                cacheDir.deleteRecursively()
                Storage.sp.edit().clear().apply()
            }
        )
    }*/


}

