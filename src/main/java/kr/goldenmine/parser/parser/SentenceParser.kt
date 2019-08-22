package kr.goldenmine.parser.parser

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.ParseContext

class SentenceParser(val parser: IParser?) : IParser {
    override fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?) {
        parser?.parse(code, parseContext, index, debug, metadata)
    }

}