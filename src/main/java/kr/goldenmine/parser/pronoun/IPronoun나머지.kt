package kr.goldenmine.parser.pronoun

import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.defaults.ObjectDouble
import kr.goldenmine.objects.objects.defaults.ObjectInteger
import kr.goldenmine.parser.VariableStorage
import kr.goldenmine.parser.predicate.PredicateStorage
import kr.goldenmine.parser.predicate.Predicate나누다
import java.util.ArrayList
import java.util.concurrent.atomic.AtomicReference

class IPronoun나머지 : IPronoun {
    private val key = PredicateStorage.INSTANCE.getPredicate("나누다")!![0] as Predicate나누다

    override val type: String
        get() = "나머지"

    override fun perform(sentence: Sentence, metadata: List<Any>?, storage: VariableStorage): Variable? {

        //println(sentence.서술어 === key)

        if (sentence.서술어 === key) {
            val variableNames = sentence.map["목적어"]!!.variables!!
            val variableKeys = sentence.map["목적어"]!!.genitiveListLastKeys
            val variableValues = sentence.map["부사어로"]!!.variables!!

            val returnVariable = AtomicReference<Variable>()

//            val variableNamesCopy = ArrayList<Variable?>(variableNames.size)
//            variableNames.forEach { if (it != null) variableNamesCopy.add(Variable(it)) else variableNamesCopy.add(null) }

            VariableStorage.setVariableAutomatically(storage, variableNames, variableKeys, variableValues) { k, v, key, r ->
                when (k.mode) {
                    VariableMode.BOOLEAN_MODE -> throw RuntimeException("Boolean은 나눌 수 없습니다.")
                    VariableMode.INT_MODE -> VariableStorage.setVariableWithKey(k, ObjectInteger(k.intValue() % v.intValue()), key)
                    VariableMode.REALNUM_MODE -> VariableStorage.setVariableWithKey(k, ObjectDouble(k.realNumValue() % v.realNumValue()), key)
                    VariableMode.STRING_MODE -> throw RuntimeException("String은 나눌 수 없습니다.")
                }
                returnVariable.set(k)
            }

            return returnVariable.get()
        } else {
            return sentence.서술어.perform(sentence, metadata, storage)
        }
    }
}
