package com.hossamelsharkawy.base.extension

import kotlinx.coroutines.*

/**
 * Created by Hossam Elsharkawy
0201099197556
on 9/25/2018.  time :04:04
 */

val globalScope = GlobalScope

fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job =
    launchOnUI { asyncAwait { block.invoke(this) } }

suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T = async(block).await()

suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> =
    globalScope.async(Dispatchers.Main) { block() }

suspend fun <T> asyncIO(block: suspend CoroutineScope.() -> T): Deferred<T> =
    globalScope.async(Dispatchers.IO) { block() }


fun launchOnUITryCatch(
    tryBlock: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
    handleCancellationExceptionManually: Boolean
) {
    launchOnUI { tryCatch(tryBlock, catchBlock, handleCancellationExceptionManually) }
}

fun launchOnUI(block: suspend CoroutineScope.() -> Unit) =
    globalScope.launch(Dispatchers.Main) { block() }


fun <T> async(io: T, ui: (T) -> Unit) = globalScope.launch(Dispatchers.IO) {
    launchOnUI {
        ui.invoke(io)
    }
}


fun <T> load(io: T, result: Result<T>) = globalScope.launch(Dispatchers.IO) {
    launchOnUI {
        if (io != null) {
            //result.onRes?.invoke(io)
        } else {
            // result.onError?.invoke(null)
        }
    }
}


fun launchOnUI(parent: Job, block: suspend CoroutineScope.() -> Unit) =
    GlobalScope.launch(Dispatchers.Main + parent) { block() }


@JvmName("launchOnUI1")
fun launchOnUI(parent: Job?, block: suspend CoroutineScope.() -> Unit): Job {
    return if (parent != null)
        GlobalScope.launch(Dispatchers.Main + parent) { block() }
    else GlobalScope.launch(Dispatchers.Main) { block() }

}


fun launch(block: suspend CoroutineScope.() -> Unit) = GlobalScope.launch { block() }


suspend fun CoroutineScope.tryCatch(
    tryBlock: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
    handleCancellationExceptionManually: Boolean = false
) {
    try {
        tryBlock()
    } catch (e: Throwable) {
        if (e !is CancellationException || handleCancellationExceptionManually) {
            catchBlock(e)
        } else {
            throw e
        }
    }
}