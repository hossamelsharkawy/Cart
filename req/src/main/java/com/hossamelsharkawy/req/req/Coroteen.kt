package com.hossamelsharkawy.req.req

import kotlinx.coroutines.*

/**
 * Created by Hossam Elsharkawy
201099197556
on 9/25/2018.  time :04:04
 */

val globalScope = GlobalScope


fun launchOnUI(parent: Job, block: suspend CoroutineScope.() -> Unit) =
    globalScope.launch(Dispatchers.Main + parent) { block() }

fun launch(block: suspend CoroutineScope.() -> Unit) = globalScope.launch { block() }

