package kr.goldenmine

import org.junit.Test
import java.util.ArrayList
import java.util.Comparator

fun <T> binarySearch(value:T, list: List<T>, comparator: Comparator<T>): Int {
    var index = list.size / 2

    while(true) {
        val currentValue = list[index]
        when(comparator.compare(value, currentValue)) {
            -1 -> { // value < currentValue
                val nextIndex = index / 2
                if(index == nextIndex) {
                    return -1
                }
                index = nextIndex
            }
            1 -> {
                // 0 1 2 3 4

                val nextIndex = (index+list.size) / 2
                if(index == nextIndex) {
                    return -1
                }
                index = nextIndex
            }
            0 -> {
                return index
            }
        }
    }
}

fun main() {
    println(binarySearch(5, listOf(1,3,4,4,4), Comparator { it, it2->Integer.compare(it, it2)}))
}
//
//
//class BinaryTest {
//    @Test
//    fun def() {
//        println(Integer.compare(5, 7))
//    }
//
//    @Test
//    fun test() {
//        val list = mutableListOf(3, 1, 2, 6, 9)
//
//        println(binarySearch(1, list, Comparator { it, it2 -> it.compareTo(it2) }))
//
//    }
//}