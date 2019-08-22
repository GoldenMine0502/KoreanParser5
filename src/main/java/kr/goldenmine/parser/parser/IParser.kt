package kr.goldenmine.parser.parser

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.Context
import kr.goldenmine.parser.ParseContext
import kr.goldenmine.parser.postposition.IPostPosition
import java.util.*

interface IParser { // ParseContext객체를 데코레이팅
    // A가 B와 같을 때까지 반복합니다
    // 까지 = 보조사
    // 때 = 대명사
    // 때가 false이면 뒷문장을 반복함


    fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?)




    class PostPositionVerify(val josa: IPostPosition?, val verify: Int)
}

fun defaultParse(source: String, josaList: List<IPostPosition>, bufferProcess: Boolean, bufferVerify: IParser.PostPositionVerify?): HashMap<String, MutableList<Context>> {
    val map = HashMap<String, MutableList<Context>>()

    val buffer = StringBuilder()
    var noParse = 0

    var start = 0
    var finish = 0

    for (i in 0 until source.length) {
        val ch = source[i]
        var parseAvailable = false
        var successParse = false

        if (ch == '\"') {
            if (noParse == 0) {
                noParse = 1
            } else if (noParse == 1) {
                noParse = 0
            }
        }
        if (ch == '\'') {
            if (noParse == 0) {
                noParse = 2
            } else if (noParse == 2) {
                noParse = 0
            }
        }

        if (noParse == 0) {
            if (ch == ' ') {
                parseAvailable = true
                finish = i
            }
            if (i == source.length - 1) {
                buffer.append(ch)
                parseAvailable = true
                finish = i + 1
            }
        }

        if (parseAvailable) {
            val josaVerify: IParser.PostPositionVerify
            if (i == source.length - 1 && bufferProcess && bufferVerify != null) {
                josaVerify = bufferVerify
            } else {
                josaVerify = getPostPosition(buffer, josaList)
            }

            val josa = josaVerify.josa

            if (josa != null) {
                val verify = josaVerify.verify

                successParse = true
                buffer.setLength(buffer.length - verify)
                map.computeIfAbsent(josa.type) { ArrayList() }.add(Context(buffer.toString(), false, start, finish - verify)) // 파싱결과 맵에 추가
                buffer.setLength(0) // clear

                start = i + 1
            }

            //                PostPositionVerify all = getPostPosition(buffer, JosaStorage.INSTANCE.getAllJosaList());
            //                if(all.getJosa() == null) {
            //                    buffer.setLength(0);
            //                }
        }
        if (!successParse) {
            buffer.append(ch)
        }

    }

    return map
}

fun getPostPosition(buffer: CharSequence, josaList: List<IPostPosition>): IParser.PostPositionVerify {
    var josa: IPostPosition? = null
    var verify = 0
    for (josaToVerify in josaList) {
        verify = josaToVerify.verify(buffer)
        if (verify >= 0) {
            josa = josaToVerify
            break
        }
    }

    return IParser.PostPositionVerify(josa, verify)
}