package kr.goldenmine

import java.io.File

fun main() {
    val file = File("test.txt")
    val writer = file.bufferedWriter()
    for(i in 1..10) {
        writer.append(i.toString())
        writer.newLine()
    }

    writer.close()

    val reader = file.bufferedReader()
    val firstRead = reader.readLine()
    reader.lines().forEach {
        println(it)
    }

    reader.close()
}