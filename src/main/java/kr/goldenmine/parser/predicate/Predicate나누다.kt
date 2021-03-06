package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.defaults.ObjectBoolean
import kr.goldenmine.objects.objects.defaults.ObjectDouble
import kr.goldenmine.objects.objects.defaults.ObjectInteger
import kr.goldenmine.parser.VariableStorage
import java.lang.RuntimeException
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class Predicate나누다 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true, true)
    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val neededSetters: List<Boolean>
        get() = listOf(false, false)

    override val optionalSetters: List<Boolean>
        get() = listOf()

    override val defaultSentence: String
        get() = "나누다"

    override val neededSentenceElements: List<String>
        get() = listOf("목적어", "부사어로")



    override val optionalSentenceElements: List<String>
        get() = emptyList()

    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.STRING, ValueScope.STRING)

    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf()

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable {
        val variableName = sentence.map["목적어"]?.variables?.first() ?: throw RuntimeException("나누다 부사어(에)가 없습니다")
        //val variableKeys = sentence.map["부사어에"]!!.genitiveListLastKeys.last()
        val variableValues = sentence.map["부사어로"]?.variables ?: throw RuntimeException("나누다 목적어가 없습니다")

        val maxMode = Integer.max(variableName.mode.mode, sentence.map["목적어"]?.variables?.filterNotNull()?.maxBy { it.mode.mode }?.mode?.mode
                ?: throw RuntimeException("더하다 목적어가 없습니다"))
        return when(maxMode) {
//            VariableMode.BOOLEAN_MODE.mode -> {
//                var value = variableName.booleanValue()
//                variableValues.forEach { value = value && it.booleanValue() }
//
//                Variable(value)
//            }
            VariableMode.BOOLEAN_MODE.mode -> throw RuntimeException("boolean은 나눌 수 없습니다.")
            VariableMode.INT_MODE.mode -> {
                var temp = variableName.intValue()
                variableValues.forEach { temp %= it!!.intValue() }
                if(temp == 0L) {
                    var value = variableName.intValue()
                    variableValues.forEach { value /= it!!.intValue() }
                    Variable(value)
                } else {
                    var value = variableName.realNumValue()
                    variableValues.forEach { value /= it!!.realNumValue() }
                    Variable(value)
                }
            }
            VariableMode.REALNUM_MODE.mode -> {
                var value = variableName.realNumValue()
                variableValues.forEach { value *= it!!.realNumValue() }

                Variable(value)
            }
            VariableMode.STRING_MODE.mode -> throw RuntimeException("String은 곱할 수 없습니다.")
//            VariableMode.STRING_MODE.mode -> {
//                var value = StringBuilder(variableName.stringValue())
//                variableValues.forEach { value.append(it.stringValue()) }
//
//                Variable(value.toString())
//            }
            else -> throw RuntimeException("unreachable code")
        }
//        val variableNames = sentence.map["목적어"]!!.variables!!
//        val variableKeys = sentence.map["목적어"]!!.genitiveListLastKeys
//        val variableValues = sentence.map["부사어로"]!!.variables!!
//
//        val returnVariable = AtomicReference<Variable>()
//
////        val variableNamesCopy = ArrayList<Variable?>(variableNames.size)
////        variableNames.forEach { if (it != null) variableNamesCopy.add(Variable(it)) else variableNamesCopy.add(null) }
//
//        VariableStorage.setVariableAutomatically(local, variableNames, variableKeys, variableValues) { k, v, key, r ->
//            //System.out.println(k + ", " + v);
//            when (k.mode) {
//                VariableMode.BOOLEAN_MODE -> throw RuntimeException("Boolean은 나눌 수 없습니다.")
//                VariableMode.INT_MODE -> VariableStorage.setVariableWithKey(k, ObjectInteger(k.intValue() / k.intValue()), key)
//                VariableMode.REALNUM_MODE -> VariableStorage.setVariableWithKey(k, ObjectDouble(k.realNumValue() / k.realNumValue()), key)
//                VariableMode.STRING_MODE -> throw RuntimeException("String은 나눌 수 없습니다.")
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
