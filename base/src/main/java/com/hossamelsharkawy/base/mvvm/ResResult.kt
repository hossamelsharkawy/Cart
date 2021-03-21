package com.hossamelsharkawy.base.mvvm



/*
sealed class AsyncResult<T> {
    object Empty : AsyncResult<>()

    data class Error(val errorMessage: String) : AsyncResult<Any?>()
    sealed class Success<out T>(val result: T) : AsyncResult<Any?>() {
        sealed class UserDataAppResult<T>(users: List<T>) : Success<List<T>>(users)
        sealed class CreateUserResult<T>(user: T) : Success<T>(user)
    }
}
*/


//typealias ResourceList<M> = Resource<ResList<M>>


/*sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Empty<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null, var refreshing: Boolean = false) : Resource<T>(data)
    class Error<T>(data: T? = null, message: String) : Resource<T>(data, message)
}*/

sealed class ResponseState<out T> {
    object Loading : ResponseState<Nothing>()
    data class Error(val errorMsg: String) : ResponseState<Nothing>()
    data class Success<T>(val item: T) : ResponseState<T>()

}

sealed class NetworkState {
    data class Success(val data: List<*>) : NetworkState()
    object InvalidData : NetworkState()
    data class Error(val error: String) : NetworkState()
    data class NetworkException(val error: String) : NetworkState()
    sealed class HttpErrors : NetworkState() {
        data class ResourceForbidden(val exception: String) : HttpErrors()
        data class ResourceNotFound(val exception: String) : HttpErrors()
        data class InternalServerError(val exception: String) : HttpErrors()
        data class BadGateWay(val exception: String) : HttpErrors()
        data class ResourceRemoved(val exception: String) : HttpErrors()
        data class RemovedResourceFound(val exception: String) : HttpErrors()
    }
}