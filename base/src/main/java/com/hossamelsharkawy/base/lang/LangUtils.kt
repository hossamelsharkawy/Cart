package com.hossamelsharkawy.base.lang

import android.content.Context
import android.content.res.Configuration

import java.util.Locale

/**
 * Created by hossam on 23/02/17.
 */

class LangUtils private constructor(context: Context, languageCode: String) {

    init {
        setLanguage(context, languageCode)
    }

    companion object {

        val AR = "ar"
        val EN = "en"

        fun getInstance(context: Context, languageCode: String): LangUtils {
            return LangUtils(context, languageCode)
        }

        fun setLanguage(context: Context, languageCode: String) {
            val config = context.resources.configuration
            val mLocale = Locale(languageCode)
            Locale.setDefault(mLocale)
            if (config.locales.get(0) != mLocale) {
                setLocale(config, mLocale)
                context.createConfigurationContext(config)
                config.setLayoutDirection(mLocale)
            }
        }

        private fun setLocale(config: Configuration, locale: Locale) {
            config.setLocale(locale)
        }
    }
}

