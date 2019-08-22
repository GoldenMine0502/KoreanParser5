package kr.goldenmine.parser.parser

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.Context
import kr.goldenmine.parser.ParseContext

class PredicateParser(private val parser: IParser?) : IParser {
    // A에 1 더합니다
    override fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?) {
        parser?.parse(code, parseContext, index, debug, metadata)

//        val parsedMap = parseContext.parsedMap
//        val contexts = parsedMap["서술어"]
//
//        if (contexts != null) {
//            for (contextIndex in 0 until contexts.size) {
//                val context = contexts[contextIndex]
//                val subContexts = splitPredicateSource(context.posStart, context.source)
//
//                contexts[contextIndex] = subContexts.last()
//                subContexts.removeAt(subContexts.size - 1)
//                parsedMap["${context.source}temp"] = subContexts
//            }
//        } else {
//            throw CompileException("서술어가 왜 존재하지 않을까")
//        }
    }

    fun splitPredicateSource(start: Int, source: String): MutableList<Context> {
        val source = "$source " // TODO 어휴 귀찮아
        val list = ArrayList<Context>()
        val buffer = StringBuilder()

        var noParse = 0
        var index = 0

        while (index < source.length) {
            val ch = source[index]
            if (ch == '\"') {
                if (noParse == 0) {
                    noParse = 1
                } else if (noParse == 1) {
                    noParse = 0
                }
            } else if (ch == '\'') {
                if (noParse == 0) {
                    noParse = 2
                } else if (noParse == 2) {
                    noParse = 0
                }
            }
            if (ch == ' ') {
                if (noParse == 0) {
                    val context = Context(buffer.toString(), false, index - buffer.length, buffer.length)
                    list.add(context)
                    buffer.setLength(0)
                }
            } else {
                buffer.append(ch)
            }
            index++
        }

        return list
    }
}
