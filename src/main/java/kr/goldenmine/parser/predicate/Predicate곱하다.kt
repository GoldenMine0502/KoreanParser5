package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.defaults.ObjectBoolean
import kr.goldenmine.objects.objects.defaults.ObjectDouble
import kr.goldenmine.objects.objects.defaults.ObjectInteger
import kr.goldenmine.parser.VariableStorage
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class Predicate곱하다 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true, true)
    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val neededSetters: List<Boolean>
        get() = listOf(false, false)

    override val optionalSetters: List<Boolean>
        get() = listOf()

    override val defaultSentence: String
        get() = "곱하다"

    override val neededSentenceElements: List<String>
        get() = listOf("부사어에", "목적어")

    override val optionalSentenceElements: List<String>
        get() = emptyList()

    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf( ValueScope.STRING, ValueScope.STRING)

    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf()

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable {
        val variableNames = sentence.map["부사어에"]!!.variables!!
        val variableKeys = sentence.map["부사어에"]!!.genitiveListLastKeys
        val variableValues = sentence.map["목적어"]!!.variables!!

        val returnVariable = AtomicReference<Variable>()

        val variableNamesCopy = ArrayList<Variable?>(variableNames.size)
        variableNames.forEach { if (it != null) variableNamesCopy.add(Variable(it)) else variableNamesCopy.add(null) }

        VariableStorage.setVariableAutomatically(local, variableNamesCopy, variableKeys, variableValues) { name, value, key, storage ->
            //System.out.println(k.getMode() + ", " + k.get());
            //System.out.println(v.getMode() + ", " + v.get());

            when (name.mode) {
                VariableMode.BOOLEAN_MODE -> {
                    val result = ObjectBoolean(name.booleanValue() && value.booleanValue())
                    VariableStorage.setVariableWithKey(name, result, key)
                }
                VariableMode.INT_MODE -> {
                    val result = ObjectInteger(name.intValue() * value.intValue())
                    VariableStorage.setVariableWithKey(name, result, key)
                }
                VariableMode.REALNUM_MODE -> {
                    val result = ObjectDouble(name.realNumValue() * value.realNumValue())
                    VariableStorage.setVariableWithKey(name, result, key)
                }
                VariableMode.STRING_MODE -> throw RuntimeException("String은 곱할 수 없습니다.")
            }
            returnVariable.set(name)
        }

        return returnVariable.get()
    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean {
        return true
    }
}
