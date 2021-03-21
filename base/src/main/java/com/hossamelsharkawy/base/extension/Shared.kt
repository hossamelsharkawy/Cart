package com.hossamelsharkawy.base.extension

import android.content.SharedPreferences

/**
 * Created by Hossam Elsharkawy
0201099197556
on 7/22/2018.  time :15:51

 */
fun SharedPreferences.add(vararg pairs: Pair<String, Any?>) {
    edit().apply {
        pairs.forEach {
            add(it)
        }
    }.apply()
}

fun SharedPreferences.add(it:  Pair<String, Any?>) {
    edit().apply {
        when (it.second) {
            null -> remove(it.first)
            is Boolean -> putBoolean(it.first, it.second as Boolean)
            is Float -> putFloat(it.first, it.second as Float)
            is Int -> putInt(it.first, it.second as Int)
            is Long -> putLong(it.first, it.second as Long)
            is String -> putString(it.first, it.second as String)
        }
    }.apply()
}