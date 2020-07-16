package kr.goldenmine.parser.parser

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.ParseContext
import kr.goldenmine.parser.parser.util.parseVariableAndConnector
import kr.goldenmine.parser.parser.util.parsedMapToIndices
import kr.goldenmine.parser.pronoun.PronounStorage

class SentencePastParser(val parser: IParser?) : IParser {
    override fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?) {
        parser?.parse(code, parseContext, index, debug, metadata)

        if(debug)
            println("executing SentencePastParser ${parseContext.parsedMap}")

        val seo = parseContext.parsedMap["서술어"]

        if (seo != null) {
            val indices = parsedMapToIndices(parseContext)

            //println("sentenceLast: $indices")

            var depth = 0

            val list = ArrayList<Int>()

            for(i in indices.size - 1 downTo 0) {
                val value = indices[i]

                val parsed = parseVariableAndConnector(value.first)

                if(value.second == "서술어") {
                    list.add(depth)
                } else {
                    // TODO 수정 필요: 해당 서술어에 포함된 context는 예외사항
                    depth = 0
                }

                parsed.forEach {
                    if(it != null) {
                        //println(it.stringValue() + ", " + PronounStorage.INSTANCE.hasPronoun(it.stringValue()))
                        if (it.isString() && PronounStorage.isPronoun(it.stringValue())) {
                            depth++
                        }
                    }
                }
            }
            list.reverse()

            //println("SentencePastParser: $list")

            parseContext.sentenceDepth.addAll(list)

            if(debug)
                println("SentencePastParser-Finish: ${parseContext.parsedMap} $list")



            /*
            // 3에 5를 더한 것에 A에 B를 더한 것과 C에 D를 더한 것을 더합니다
            이런문장을 제대로 파싱하기는 어렵다.
             */


            /*
            1. 부사 '함께'는 주로 ‘…과 함께’의 구성으로 쓴다고 답변을 드렸습니다.
사전 정보에서 '주로'라는 표현을 쓴 것은, 반드시 ‘…과 함께’의 구성으로만 쓴다는 것은 아닙니다.
'함께'의 용례로 "온 가족이 함께 여행을 간다."라는 문장이 있다는 점도 살펴보시기 바랍니다.
보이신 (1)과 (2)는 의미상 큰 차이가 없어 보입니다.
             */


            /*
일단 구해야하는게
얼마나 안겨져있는지
depth를 구해야겠지
[[A가 6보다 크고 B가 3보다 크다는 것을 출력]]하고 [[C를 1로 설정합니다]] = 이결과도 true/false가 돼버림
A가 6보다 크고 = 1
B가 3보다 크다는 것 = 1
출력하고 = 0
이런식
1이 될 조건 = 서술어 바로 뒤에 대명사가 있다?
대명사 ex) 것, 때, 값 ....
어떰?
그리고
~~고 이런말이 나오면
바로앞문장 depth 따라가는거임
             */

            // boolean 뒤의 접속사 -> AND, OR의 의미
            // 이외의 접속사 -> 그냥 접속사의 의미
            // 3에 5를 더한 것이 6보다 크거나 같다는 것보다 작고
            //
            // {{3에 5를 더한 것}이 6보다 크거나 같다는 것}을 출력하고 {3에 5를 더한 것}을 [A]에 저장합니다
            // {{true false} true (and) true false} true / {true  false} true
        }

    }


}