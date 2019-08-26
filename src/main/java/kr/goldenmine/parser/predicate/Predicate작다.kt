package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.parser.VariableStorage
import java.util.HashMap

// A를 출력하고 5에 [A]를 3을 더한 것으로 설정합니다

class Predicate작다 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true, true)
    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val neededSetters: List<Boolean>
        get() = listOf(false, false)

    override val optionalSetters: List<Boolean>
        get() = listOf()
    override val defaultSentence: String
        get() = "작다"
    override val neededSentenceElements: List<String>
        get() = listOf("주어", "비교")
    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.STRING, ValueScope.STRING)
    override val optionalSentenceElements: List<String>
        get() = listOf()
    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf()

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable? {
        val subject = sentence.map["주어"]!!
        val compare = sentence.map["비교"]!!
        // a  b c d e
        // 5 10 3 1 2

        //println("$subject // $compare")

        subject.variables!!.forEach {
            compare.variables!!.forEach { it2 ->
                if((it == null || it2 == null) || !it.smallerThan(it2)) {
                    return Variable(value = false, isConst = true)
                }
            }
        }

        return Variable(value = true, isConst = false)
    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean = true

}
