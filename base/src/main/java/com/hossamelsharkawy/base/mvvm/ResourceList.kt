package com.hossamelsharkawy.base.mvvm


typealias ReqMap = HashMap<String, Any>?






sealed class ResourceList<T>(
    val data: ArrayList<T>? = null,
    val message: String? = null
) {
    class Empty<T>(message :String? = null) : ResourceList<T>(message = message)
    class Success<T>(data: ArrayList<T>) : ResourceList<T>(data)
    class Loading<T>(var refreshing: Boolean = false) : ResourceList<T>()
    class Error<T>(message: String) : ResourceList<T>(message = message)
}