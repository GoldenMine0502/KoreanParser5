package kr.goldenmine.parser

import java.util.*

class Code(sources: List<String>) {
    val sourceCode: MutableList<ParseContext>

    init {
        sourceCode = ArrayList()

        for (i in sources.indices) {
            sourceCode.add(ParseContext(sources[i]))
        }
    }
}
