package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.Variable
import kr.goldenmine.parser.VariableStorage
import java.util.*

class Predicate출력하다 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true)
    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val defaultSentence: String
        get() = "출력하다"

    override val neededSentenceElements: List<String>
        get() = listOf("목적어")

    override val optionalSentenceElements: List<String>
        get() = emptyList()

    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.STRING)

    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf()

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable? {
        val map = sentence.map
        val 목적어 = map["목적어"]!!.variables!!
        for (i in 목적어.indices) {
            println(목적어[i]?.get())
        }
        if (목적어.size == 1) {
            return 목적어[0]
        } else if (목적어.size > 1) {
            val sb = StringBuilder()
            sb.append(목적어[0]?.get())
            for (i in 1 until 목적어.size) {
                sb.append("\n")
                sb.append(목적어[i]?.get())
            }

            return Variable(sb.toString(), false)
        } else {
            return null
        }
    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean {
        return true
    }
}
