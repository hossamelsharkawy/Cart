package com.hossamelsharkawy.simplecart.app.util.util

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap


fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}

fun <T> SnapshotStateList<T>.refresh() {
    with(arrayListOf<T>()) {
        addAll(this@refresh)
        this@refresh.clear()
        this@refresh.addAll(this)
    }
}

fun <K, V> SnapshotStateMap<K, V>.swapList(mutableMap: MutableMap<K, V>) {
    entries.clear()
    putAll(mutableMap)
}

