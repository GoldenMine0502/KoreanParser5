package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.parser.VariableStorage
import java.util.HashMap

open class Predicate제거하다 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(false)
    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val neededSetters: List<Boolean>
        get() = listOf(false)

    override val optionalSetters: List<Boolean>
        get() = listOf()
    override val defaultSentence: String
        get() = "제거하다"
    override val neededSentenceElements: List<String>
        get() = listOf("목적어")
    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.VARIABLE)
    override val optionalSentenceElements: List<String>
        get() = listOf()
    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf()

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable? {
        sentence.map["목적어"]!!.variables!!.forEach {
            if(it != null) {
                val name = it.stringValue()
                if(name != null) {
                    local.removeVariable(name.substring(1, name.length - 1))
                }
            }
        }
        return null
    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean {
        return true
    }

}