package kr.goldenmine.parser.postposition

import kr.goldenmine.parser.ParseContext

class IPostPosition서술어 : IPostPosition {
    val defaultWord = "TEST"

    override val type: String
        get() = "서술어"

    override val josaCommunity: JosaCommunity
        get() = JosaCommunity.격조사

    override fun verify(lastSeq: CharSequence): Int {
        val builder = StringBuilder()

        var mode = 0

        for(ch in lastSeq) {
            if(ch == '\'') {
                if(mode == 0) {
                    mode = 1
                } else if(mode == 1) {
                    mode = 0
                    builder.append(defaultWord)
                }
                continue
            }
            if(ch == '\"') {
                if(mode == 0) {
                    mode = 2
                } else if(mode == 2) {
                    mode = 0
                    builder.append(defaultWord)
                }
                continue
            }

            if(mode == 0)
                builder.append(ch)
        }

        val lastToStr = builder.toString()

        //println("lasttostr서술어: $lastToStr")



        val analyzeResultList = ParseContext.komoran.analyze(lastToStr)
        val list = analyzeResultList.list
        //Pair<String, String> first = list.get(0);
        val last = list[list.size - 1]

        var verified = false

        when (last.second) {
            "EC", "ETM", "ETN", "JX" -> when (last.first) {
                "까지" -> {}
                else -> verified = true
            }
            else -> when (last.first) {
                "라면" -> verified = true
            }
        }


        if(verified) {
            //println(list)
            for (value in list) {
                when (value.second) {
                    "VV", "VA", "XSV" -> return 0
                }
            }
        }
        //같다면
        //더한
        //뺀
        //3이라면


        //return 0;

        return -1
    }
}
