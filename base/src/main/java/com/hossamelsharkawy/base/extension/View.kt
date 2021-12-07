package com.hossamelsharkawy.base.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.LayoutRes

/**
 * Created by Hossam Elsharkawy
0201099197556
on 9/11/2018.  time :14:13

 */
fun View.isVisible() = this.visibility == View.VISIBLE

fun View.isGone() = this.visibility == View.GONE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    if (isVisible()) {
        this.visibility = View.GONE
    }
}

fun View.visibleIf(boolean: Boolean) = if (boolean) visible() else gone()
fun View.visibleIf(boolean: Boolean?) = if (boolean==true) visible() else gone()
fun View.enableIf(boolean: Boolean) = if (boolean) enable() else disable()

fun View.visibleOrInvIf(boolean: Boolean) = if (boolean) visible() else invisible()

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.disable() {
    this.isEnabled = false
}

fun View.enable() {
    this.isEnabled = true
}


fun android.widget.TextView.visibleIf(value: String?) {
    if (!value.isNullOrEmpty()) {
        this.visible()
        this.text = value
    } else this.gone()
}


fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)