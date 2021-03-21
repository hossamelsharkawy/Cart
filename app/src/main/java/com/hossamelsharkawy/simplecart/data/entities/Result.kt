package com.hossamelsharkawy.simplecart.data.entities

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Resource<*>.succeeded
    get() = this is Resource.Success && data != null


val Resource<*>.isSuccess
    get() = this is Resource.Success && data != null


suspend fun <T> List<T>.asyncToResource(coroutineDispatcher: CoroutineDispatcher =Dispatchers.IO) =
    withContext(coroutineDispatcher) {
        return@withContext try {
            Resource.Success(this@asyncToResource)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


fun <T> Resource<T>.onSusses(block: (T) -> Unit): Resource<T> {
    if (this is Resource.Success)
        block(this.data)
    return this
}


/*
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

val Resource<*>.succeeded
    get() = this.status==Status.SUCCESS*/
