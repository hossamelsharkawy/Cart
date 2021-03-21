package com.hossamelsharkawy.req


import com.google.gson.JsonParser
import com.hossamelsharkawy.req.req.*
import kotlinx.coroutines.Deferred


/**
 * Created by Hossam Elsharkawy
on 6/14/2018.
 */


/*************************************/

private const val errorMsg = " No Results."

/*fun <T> Deferred<T>.req() = launchOnUI(parent = Server.ApiJob) {
    doReq(this@req)
}*/

/*fun <T> Deferred<ResourceList<T>>.reqOnUI() = launchOnUI(parent = Server.ApiJob) {
    doReq(this@reqOnUI)
}*/

suspend fun <T> Deferred<ResourceList<T>>.fetchApi(): ResourceList<T> {
    return try {
        onResDataList(await())
    } catch (e: Exception) {
        /*if (e !is java.util.concurrent.CancellationException) {
            e.printStackTrace()
            return ResourceList.Error(message = getErrorMsg(e))
        }*/
        e.printStackTrace()

        ResourceList.Error(message = getErrorMsg(e))
    }
}




/*fun <T> onResData(
    response: ResData<*>
) {
    with(resCallback) {
        if (this == null) return
        if (response.data != null) {
            onResponse?.invoke(response as T)
        } else {
            onEmpty?.invoke(errorMsg) ?: onError?.invoke(errorMsg)
        }
    }
}*/

fun <T> onResDataList(resourceList: ResourceList<T>): ResourceList<T> = with(resourceList) {
    return if (!data.isNullOrEmpty()) {
        ResourceList.Success(data)
    } else {
        ResourceList.Empty()
    }
}

fun <T> onResDataPag(
    response: ResListPag<*>, resCallback: ReqCallback<T>?
) {
    with(resCallback) {
        if (this == null) return
        if (!response.rows.isNullOrEmpty()) {
            onResponse?.invoke(response as T)
        } else {
            onEmpty?.invoke(errorMsg) ?: onError?.invoke(errorMsg)
        }
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
        //lis?.onHttpError(e.localizedMessage)
    }
}
/*
 {"status":"error","messages":{"warranty_months":["The warranty months must be a number.","The warranty months must be between 0 and 240 digits."]},"payload":null}
* */

/*
{"api_status":0,"api_http":422,"api_message":{"title":"error","message":"The Field Phone  must started with 0"}}*/


fun getApiMessage(e: retrofit2.HttpException): String {

    val errorResponse = e.response()?.errorBody()?.string()

    if (errorResponse != null) {
        val parser = JsonParser()

        val json = parser.parse(errorResponse).asJsonObject

        val msg = json.get("api_message")?.asJsonObject?.get("message")?.asString
        //val msg = json.get("messages")?.toString()
        return msg ?: json.toString()


    }

    if (e.message != null) return e.message!!

    return "Api Error"

}
