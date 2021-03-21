package com.hossamelsharkawy.base.net

import android.app.Application
import com.hossamelsharkawy.base.lang.LangUtils
import kotlinx.coroutines.Job
import okhttp3.OkHttpClient

/**
 * Created by Hossam Elsharkawy
0201099197556
on 8/27/2018.  time :11:40

 */
object BaseApp {

    lateinit var client:OkHttpClient

    lateinit var app: Application
    var logo: Int = 0
    lateinit var onCrashAction: () -> Unit


    var lang = LangUtils.EN

    fun isEn() =lang ==  LangUtils.EN
    fun init(app: Application, client:OkHttpClient,  logo: Int , onCrashAction: () -> Unit ) {
        this.app = app
        this.onCrashAction = onCrashAction
        this.logo = logo
        this.client = client
    }

    fun stopReq() {
        // server = Job()
    }
}