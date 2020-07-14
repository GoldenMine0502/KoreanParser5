package kr.goldenmine.parser.parser.disabled

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.Context
import kr.goldenmine.parser.ParseContext
import kr.goldenmine.parser.parseVariable
import kr.goldenmine.parser.parser.IParser
import kr.goldenmine.parser.parser.defaultParse
import kr.goldenmine.parser.postposition.IPostPosition
import kr.goldenmine.parser.postposition.JosaCommunity
import kr.goldenmine.parser.postposition.JosaStorage
import java.util.*
import java.util.stream.Collectors

class BoeoParser(private val parser: IParser?) : IParser {
    private var postPosition: IPostPosition? = null

    init {

        val list = JosaStorage.getJosaList(JosaCommunity.격조사)
        for (postPosition in list) {
            if (postPosition.type == "서술어") {
                this.postPosition = postPosition
                break
            }
        }
    }

    override fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?) {
        parser?.parse(code, parseContext, index, debug, metadata)

        if (!parseContext.isNoParse) {
            val verify = IParser.PostPositionVerify(postPosition, 0)
            val 접속조사리스트 = JosaStorage.getJosaList(JosaCommunity.접속조사)
            val contextMap = parseContext.parsedMap

            var srcIndex = 0

            if (contextMap.containsKey("서술어")) {

                for (srcContext in contextMap["서술어"]!!) {
                    val src = srcContext.source

                    val result = defaultParse(src, 접속조사리스트, true, verify)
                    val boeo = result["접속조사"]

                    if (boeo != null) {
                        var sum = 0
                        for (i in boeo.indices) {
                            if (i != boeo.size - 1) {
                                sum++
                            }
                            sum += boeo[i].posFinish
                        }

                        val context = Context(src.substring(0, sum), false, srcContext.posStart, srcContext.posStart + sum)
                        context.variables = boeo.stream().map { parseVariable(it.source) }.collect(Collectors.toList())

                        val seo = src.substring(sum + 2)
                        val context2 = Context(seo, false, srcContext.posStart + sum + 2, srcContext.posFinish)

                        val map = parseContext.parsedMap

                        map.computeIfAbsent("보어") { ArrayList() }.add(context)

                        val value = map["서술어"]?:throw RuntimeException("서술어가 존재하지 않습니다")
                        value[srcIndex] = context2

                        srcIndex++
                    }
                }
            } else {
                println("컴파일 경고: 서술어가 존재하지 않음")
            }
        }
    }
}
