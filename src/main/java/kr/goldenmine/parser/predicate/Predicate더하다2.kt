package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.parser.VariableStorage
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class Predicate더하다2 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true)

    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val neededSetters: List<Boolean>
        get() = listOf(false)

    override val optionalSetters: List<Boolean>
        get() = listOf()

    override val defaultSentence: String
        get() = "더하다"

    override val neededSentenceElements: List<String>
        get() = listOf("목적어")

    override val optionalSentenceElements: List<String>
        get() = emptyList()

    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.STRING)

    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf()

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable? {
        val variableValues = sentence.map["목적어"]!!.variables!!

        val firstVariable = variableValues[0]
        if(firstVariable != null) {
            var firstVariableCopy = firstVariable.get().clone()
            for(i in 1 until variableValues.size) {
                firstVariableCopy = firstVariableCopy.add(variableValues[i]!!.get())
            }

            return Variable(firstVariableCopy)
        }
        //println(returnVariable.get().get())

        return null
    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean {
        return true
    }
}
