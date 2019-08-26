package kr.goldenmine.parser

import kr.goldenmine.parser.predicate.IPredicate
import kr.goldenmine.parser.predicatespecific.IPredicateSpecific
import java.util.*

// IPredicate, IPredicateSpecific는 Sentence 클래스와 상호 참조중.
// 논리적 문제인지 설계상 어쩔 수 없는 지는 확인해야 함.

class Sentence(val map: HashMap<String, Context>, val 서술어: IPredicate, val specific: IPredicateSpecific?) {
    val originalMap = HashMap<String, Context>()

    val changeVar = HashMap<String, Boolean>()
    val setter = HashMap<String, Boolean>()

    init {
        val needed = 서술어.neededSentenceElements
        val neededReplace = 서술어.neededReplaceVariable
        val neededSetter = 서술어.neededSetters
        val select = 서술어.optionalSentenceElements
        val selectReplace = 서술어.optionalReplaceVariable
        val selectSetter = 서술어.optionalSetters

        try {
            for (i in needed.indices) {
                changeVar[needed[i]] = neededReplace[i]
                setter[needed[i]] = neededSetter[i]
            }

            for (i in select.indices) {
                changeVar[select[i]] = selectReplace[i]
                setter[needed[i]] = selectSetter[i]
            }
        } catch(ex: Exception) {
            println("error on initializing sentence: ${서술어.defaultSentence}")
            ex.printStackTrace()
        }
    }

    var multiProcessData: IPredicateSpecific.MultiProcessData? = null

    var isLast: Boolean = false

    override fun toString(): String {
        return 서술어.defaultSentence + ": " + map
    }
}
