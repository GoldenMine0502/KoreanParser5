package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.Variable
import kr.goldenmine.parser.VariableStorage
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class Predicate더하다 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true, true)

    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val defaultSentence: String
        get() = "더하다"

    override val neededSentenceElements: List<String>
        get() = listOf("부사어에", "목적어")

    override val optionalSentenceElements: List<String>
        get() = emptyList()

    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.STRING, ValueScope.STRING)

    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf()

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable {
        val variableNames = sentence.map["부사어에"]!!.variables!!
        val variableValues = sentence.map["목적어"]!!.variables!!

        val returnVariable = AtomicReference<Variable>()

        // TODO (최적화) 그냥 returnVariable 만들고 k v r 세 매개변수 활용

        // TODO 음믕음믕음믕음믕음믕음믕

        val variableNamesCopy = ArrayList<Variable?>(variableNames.size)
        variableNames.forEach { if (it != null) variableNamesCopy.add(Variable(it)) else variableNamesCopy.add(null) }

        VariableStorage.setVariableAutomatically(local, variableNamesCopy, variableValues) { k, v, r ->
            when (k.mode) {
                Variable.VariableMode.BOOLEAN_MODE -> throw RuntimeException("Boolean은 더할 수 없습니다.")
                Variable.VariableMode.INT_MODE -> k.set(k.intValue() + v.intValue())
                Variable.VariableMode.REALNUM_MODE -> k.set(k.realNumValue() + v.realNumValue())
                Variable.VariableMode.STRING_MODE -> k.set(k.stringValue()!! + v.stringValue()!!)
            }
            returnVariable.set(k)
        }

        //println(returnVariable.get().get())

        return returnVariable.get()
    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean {
        return true
    }
}
