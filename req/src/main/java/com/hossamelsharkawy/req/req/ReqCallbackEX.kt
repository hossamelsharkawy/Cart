package com.hossamelsharkawy.req.req


/**
 * Created by Hossam Elsharkawy
0201099197556
on 9/26/2018.  time :16:29

 */

/*
typealias ReqCallback<T> = (onResponse: ((T) -> Unit), onError: ((msg: String) -> Unit), onEmpty: ((String?) -> Unit)?) -> Unit
*/


class ReqCallback<T>(
    val onResponse: ((T) -> Unit)? = null,
    val onError: ((msg: String) -> Unit)? = null,
    val onEmpty: ((String?) -> Unit)? = null
)


fun <T> ReqCallback<T>.inn(secOnResponse: ((T) -> Unit)?): ReqCallback<T> {
    return ReqCallback(
        onResponse = {
            secOnResponse?.invoke(it)
            onResponse?.invoke(it)
        }, onError = onError, onEmpty = onEmpty
    )
}

fun <T> ReqCallback<T>.inn(secReqCallback: ReqCallback<T>?): ReqCallback<T> {
    return ReqCallback(
        onResponse = {
            secReqCallback?.onResponse?.invoke(it)
            onResponse?.invoke(it)
        }, onError = {
            secReqCallback?.onError?.invoke(it)
            onError?.invoke(it)
        }, onEmpty = {
            secReqCallback?.onEmpty?.invoke(it ?: "")
            onEmpty?.invoke(it ?: "")
        }
    )
}

fun <T> ReqCallback<T>.mapOnEmpty(dummyRes: (() -> T)?) = ReqCallback(
    onResponse = onResponse
    , onError = onError
    , onEmpty = {
        if (dummyRes != null) {
            onResponse?.invoke(dummyRes())
        }
    }
)
/*

fun <T> ReqList<T>.setDummyData(arrayList: ArrayList<T>) {
    onResponse!!.invoke(ResList<T>().apply {
        rows = arrayList
    })
}
*/
