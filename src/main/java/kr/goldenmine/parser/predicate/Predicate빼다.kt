package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.defaults.ObjectDouble
import kr.goldenmine.objects.objects.defaults.ObjectInteger
import kr.goldenmine.objects.objects.defaults.ObjectString
import kr.goldenmine.parser.VariableStorage
import java.lang.RuntimeException
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class Predicate빼다 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true, true)
    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val neededSetters: List<Boolean>
        get() = listOf(false, false)

    override val optionalSetters: List<Boolean>
        get() = listOf()
    override val defaultSentence: String
        get() = "빼다"

    override val neededSentenceElements: List<String>
        get() = Arrays.asList("부사어에서", "목적어")

    override val optionalSentenceElements: List<String>
        get() = emptyList()

    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.STRING, ValueScope.STRING)

    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf()

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable {
        val variableName = sentence.map["부사어에서"]?.variables?.last() ?: throw RuntimeException("빼다 부사어(에서)가 없습니다")
        //val variableKey = sentence.map["부사어에서"]!!.genitiveListLastKeys
        val variableValue = sentence.map["목적어"]?.variables?.last() ?: throw RuntimeException("빼다 목적어가 없습니다")

        return when(Integer.max(variableName.mode.mode, variableValue.mode.mode)) {
            VariableMode.BOOLEAN_MODE.mode -> throw RuntimeException("Boolean은 더할 수 없습니다.")
            VariableMode.INT_MODE.mode -> Variable(ObjectInteger(variableName.intValue() - variableValue.intValue()))
            VariableMode.REALNUM_MODE.mode -> Variable(ObjectDouble(variableName.realNumValue() - variableValue.realNumValue()))
            VariableMode.STRING_MODE.mode -> throw RuntimeException("String은 뺄 수 없습니다.")
            else -> throw RuntimeException("unreachable code")
        }

//        val returnVariable = AtomicReference<Variable>()
//
////        val variableNamesCopy = ArrayList<Variable?>(variableNames.size)
////        variableNames.forEach { if (it != null) variableNamesCopy.add(Variable(it)) else variableNamesCopy.add(null) }
//
//        VariableStorage.setVariableAutomatically(local, variableNames, variableKeys, variableValues) { k, v, key, r ->
//            when (k.mode) {
//                VariableMode.BOOLEAN_MODE -> throw RuntimeException("Boolean은 뺄 수 없습니다.")
//                VariableMode.INT_MODE -> {
//                    VariableStorage.setVariableWithKey(k, ObjectInteger(k.intValue() - v.intValue()), key)
//                }
//                VariableMode.REALNUM_MODE -> {
//                    VariableStorage.setVariableWithKey(k, ObjectDouble(k.realNumValue() - v.realNumValue()), key)
//                }
//                VariableMode.STRING_MODE -> throw RuntimeException("String은 뺄 수 없습니다.")
//            }
//            returnVariable.set(k)
//        }
//
//        return returnVariable.get()
    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean {
        return true
    }
}
