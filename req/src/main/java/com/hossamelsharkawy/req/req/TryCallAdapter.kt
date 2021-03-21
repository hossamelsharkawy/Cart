package com.hossamelsharkawy.req.req
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.io.EOFException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * Created by Hossam Elsharkawy
on 6/24/2018.  time :11:49
 */

class TryCallAdapter constructor(val active401: Boolean) : CallAdapter.Factory() {

    companion object {
        @JvmStatic
        @JvmName("create")
        operator fun invoke() = TryCallAdapter(true)
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Deferred::class.java != getRawType(returnType)) {
            return null
        }
        if (returnType !is ParameterizedType) {
            throw IllegalStateException(
                "Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>"
            )
        }
        val responseType = getParameterUpperBound(0, returnType)

        val rawDeferredType = getRawType(responseType)

        return if (rawDeferredType == Response::class.java) {
            if (responseType !is ParameterizedType) {
                throw IllegalStateException(
                    "Response must be parameterized as Response<Foo> or Response<out Foo>"
                )
            }

            ResponseCallAdapter<Any>(getParameterUpperBound(0, responseType))
        } else {

            BodyCallAdapter<Any>(responseType, active401)

        }
    }

    private class BodyCallAdapter<T>(
        private val responseType: Type,
        val active404: Boolean


    ) : CallAdapter<T, Deferred<T>> {
        private var tryCount: Int = 0

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<T> {
            val deferred = CompletableDeferred<T>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }

            req(deferred, call)

            return deferred
        }

        private fun req(deferred: CompletableDeferred<T>, call: Call<T>) {

            //Log.d("BodyCallAdapter", hashCode().toString() +" tryCount : $tryCount")
            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    if (t is EOFException || t is java.net.SocketTimeoutException) {
                        retry(deferred, call.clone(), t)
                    } else {
                        deferred.completeExceptionally(t)
                    }
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        deferred.complete(body)
                    } else {
                        // : HTTP 401
                        if (response.code() != 401)
                            deferred.completeExceptionally(HttpException(response))
                        else {
                            if (active404)
                                deferred.completeExceptionally(HttpException(response))
                         //   ll("a7a $response")
                        }
                    }
                }
            })
        }

        private fun retry(deferred: CompletableDeferred<T>, call: Call<T>, t: Throwable) {
            if (tryCount < 2) {
                tryCount++
                req(deferred, call.clone())
            } else {
                tryCount = 0
                deferred.completeExceptionally(t)
            }
        }
    }

    private class ResponseCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Deferred<Response<T>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Deferred<Response<T>> {
            val deferred = CompletableDeferred<Response<T>>()

            deferred.invokeOnCompletion {
                if (deferred.isCancelled) {
                    call.cancel()
                }
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    deferred.completeExceptionally(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    deferred.complete(response)
                }
            })

            return deferred
        }
    }
}

