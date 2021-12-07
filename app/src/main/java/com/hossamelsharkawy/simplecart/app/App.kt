package com.hossamelsharkawy.simplecart.app

import Storage
import android.app.Application
import androidx.navigation.ActivityNavigator
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.hossamelsharkawy.base.net.BaseApp
import com.hossamelsharkawy.simplecart.R
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import javax.inject.Inject



@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var provideOkHttpClient: OkHttpClient

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
        setBase()


    }

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
    }


}

