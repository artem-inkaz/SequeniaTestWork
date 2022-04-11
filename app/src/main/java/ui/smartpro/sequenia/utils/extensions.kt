package ui.smartpro.sequenia.utils

import android.view.View

fun <T> convertToSet(list: List<T>): Set<T> {
    return list.toSet()
}

fun <T> convertToList(set: Set<T>): List<T> {
    return ArrayList(set)
}

fun View.click(click: () -> Unit) = setOnClickListener { click() }