package kr.goldenmine

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran

fun main(args: Array<String>) {
    val komoran = Komoran(DEFAULT_MODEL.FULL)
    komoran.setFWDic("user_data/fwd.user")
    komoran.setUserDic("user_data/dic.user")

    val input = "밀리언 달러 베이비랑 바람과 함께 사라지다랑 뭐가 더 재밌었어?"
    val analyzeResultList = komoran.analyze(input)
    val tokenList = analyzeResultList.tokenList

    // 1. print each tokens by getTokenList()
    println("==========print 'getTokenList()'==========")
    for (token in tokenList) {
        println(token)
        println(token.morph + "/" + token.pos + "(" + token.beginIndex + "," + token.endIndex + ")")
        println()
    }

    // 2. print nouns
    println("==========print 'getNouns()'==========")
    println(analyzeResultList.nouns)
    println()

    // 3. print analyzed result as pos-tagged text
    println("==========print 'getPlainText()'==========")
    println(analyzeResultList.plainText)
    println()

    // 4. print analyzed result as list
    println("==========print 'getList()'==========")
    println(analyzeResultList.list)
    println()

    // 5. print morphes with selected pos
    println("==========print 'getMorphesByTags()'==========")
    println(analyzeResultList.getMorphesByTags("NP", "NNP", "JKB"))
}
