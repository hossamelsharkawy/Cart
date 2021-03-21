package com.hossamelsharkawy.base.extension

import androidx.lifecycle.MutableLiveData
import com.hossamelsharkawy.base.mvvm.SingleLiveEvent

/**
 * Created by Hossam Elsharkawy
0201099197556
on 9/30/2018.  time :16:49

 */

val <T> T.toMutableLiveData: MutableLiveData<T>
    get() = MutableLiveData<T>().default(this)


val <T> T.toSingleMutableLiveData: MutableLiveData<T>
    get() = SingleLiveEvent<T>().default(this)

private fun <T> MutableLiveData<T>.default(value: T): MutableLiveData<T> {
    this.value = value
    return this
}




