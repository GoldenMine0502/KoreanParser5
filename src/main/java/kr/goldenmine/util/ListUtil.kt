package kr.goldenmine.util

val <T> MutableList<T>.last
get() = this[size - 1]

//fun <T> MutableList<T>.getLast(): T = this[size - 1]