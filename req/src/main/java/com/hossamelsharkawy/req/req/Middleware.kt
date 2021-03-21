package com.hossamelsharkawy.req.req

import com.hossamelsharkawy.req.getApiMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


val coroutineDispatcher = Dispatchers.IO


suspend fun <T> async(block: suspend () -> ListModel<T>) =
    withContext(coroutineDispatcher) {
        return@withContext try {
            val data = block().data

            if (data.isNullOrEmpty()) {
                ResourceList.Success(data!!)
            } else {
                ResourceList.Empty()
            }
        } catch (e: Exception) {
            ResourceList.Error(getErrorMsg(e))
        }
    }

suspend fun <T> ListModel<T>.async() =
    withContext(coroutineDispatcher) {
        return@withContext try {
            val data = this@async.data

            if (data.isNullOrEmpty()) {
                ResourceList.Success(data!!)
            } else {
                ResourceList.Empty()
            }
        } catch (e: Exception) {
            ResourceList.Error(getErrorMsg(e))
        }
    }

private fun getErrorMsg(t: retrofit2.HttpException) =
    when (t.code()) {
        400 -> getApiMessage(t)
        404 -> t.message()
        429 -> t.message()
        422 -> getApiMessage(t)
        403 -> getApiMessage(t)
        504 -> Server.serverErrorMsg
        401 -> Server.invalid_credentialsMsg
        else -> Server.serverErrorMsg
    }


private fun getErrorMsg(e: Exception): String {
    return when (e) {
        is retrofit2.HttpException -> getErrorMsg(e)
        is java.net.SocketTimeoutException -> Server.socketTimeoutMsg
        is javax.net.ssl.SSLHandshakeException -> Server.connectionErrorMsg
        is com.google.gson.JsonSyntaxException -> "Api Error" + "\n${e.message}"
        is java.net.UnknownHostException -> e.message ?: "Unknown Host!"
        is java.net.ProtocolException -> Server.protocolExceptionMsg
        is javax.net.ssl.SSLException -> Server.connectionAbortMsg
        is java.net.SocketException -> Server.connectionAbortMsg
        is java.io.EOFException -> Server.connectionErrorMsg
        is java.util.concurrent.CancellationException -> Server.cancellationExceptionMsg
        //  else -> throw Exception(e)
        else -> e.message ?: e.javaClass.name
    }
}
//lis?.onHttpError(e.localizedMessage)

fun <T> ResourceList<T>.onSusses(block: () -> Unit): ResourceList<T> {
    if (this is ResourceList.Success)
        block()
    return this
}

fun <T> ResourceList<T>.onError(block: () -> Unit): ResourceList<T> {
    if (this is ResourceList.Error)
        block()
    return this
}