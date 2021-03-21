package com.hossamelsharkawy.base.extension

import kotlinx.coroutines.delay

/**
 * Created by Hossam Elsharkawy
0201099197556
on 20/11/18.  time :10:54

 */

fun <A> Collection<A>.forEachParallelDelay(block: suspend (A) -> Unit) = forEachIndexed { index, a ->
    launchAsync {
        delay((index * 10).toLong())
        block.invoke(a)
    }
}

/*fun <A> Collection<A>.forEachParallelDelayIndex(block: suspend (A ,Int) -> Unit) = forEachIndexed { index, a ->
    launchAsync {
        delay((index * 10).toLong())
        block.invoke(a ,index)
    }
}*/
@Synchronized
fun <A> Collection<A>.forEachParallelDelayIndex(block: suspend (A, Int) -> Unit) = launchAsync {

    forEachIndexed { index, a ->
        delay((index * 5).toLong())
        block.invoke(a, index)
    }
}

fun <A> Collection<A>.forEachParallelDelay(block: suspend (A) -> Unit, timeMillis: Long) = forEachIndexed { index, a ->
    launchAsync {
        delay((index * timeMillis))
        block.invoke(a)
    }
}

@Synchronized
fun <A> Collection<A>.forEachParallel(block: suspend (A) -> Unit) = forEach {
    launchAsync { block.invoke(it) }
}

@Synchronized
fun <A> ArrayList<A>.forEachParallelAfterFirst(block: suspend (A) -> Unit) {
    if (this.size > 0) {
        launchOnUI {block.invoke(get(0))}
        if (this.size > 1) {
            for (i in 1 until this.size) {
                launchAsync { block.invoke(get(i)) }
            }
        }
    }
}


fun <T> ArrayList<T>.replaceItem(t: T): Boolean {
    val index = indexOf(t)
    if (index != -1) {
        set(index, t)
        return true
    }
    return false
}


/*
fun <A> Collection<A>.forEachParallel(f: suspend (A) -> Unit): Unit = runBlocking {
    map { async(coroutineContext) { f(it) } }.forEach { it.await() }
}
*/