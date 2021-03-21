package com.hossamelsharkawy.base.lang

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import java.util.*

/**
 * Created by Hossam Elsharkawy
0201099197556
on 02/10/19.  time :13:16

 */
object LocaleHelper {




    fun setLocale(context: Context, language: String) {
        //ll("langTest setLocale: $language")
        val  myLocale = Locale(language)
        val res = context.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLayoutDirection(myLocale)
        conf.setLocale(myLocale)
        Locale.setDefault(myLocale)
        res.updateConfiguration(conf, dm)



        /*   return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               updateResources(context, language)
           } else updateResourcesLegacy(context, language)*/
    }


    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}
