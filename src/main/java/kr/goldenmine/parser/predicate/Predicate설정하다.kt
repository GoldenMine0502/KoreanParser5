package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.objects.objects.defaults.*
import kr.goldenmine.parser.VariableStorage
import kr.goldenmine.parser.VariableStorage.Companion.setVariableAutomatically
import java.util.*
import java.util.concurrent.atomic.AtomicReference

open class Predicate설정하다 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true, true)
    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val neededSetters: List<Boolean>
        get() = listOf(true, false)

    override val optionalSetters: List<Boolean>
        get() = listOf()
    override val defaultSentence: String
        get() = "설정하다"

    override val neededSentenceElements: List<String>
        get() = listOf("목적어", "부사어로")

    override val optionalSentenceElements: List<String>
        get() = emptyList()

    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.VARIABLE, ValueScope.STRING)

    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf()

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable {
//        val context목적어 = sentence.map["목적어"]!!
//        val context부사어로 = sentence.map["부사어로"]!!
//
//        val variableKeys = context목적어.variables!!
//        val variableValues = context부사어로.variables!!

        val variableNames = sentence.map["목적어"]!!.variables!!
        val variableKeys = sentence.map["목적어"]!!.genitiveListLastKeys
        val variableValues = sentence.map["부사어로"]!!.variables!!

        //println(variableNames)

        val returnVariable = AtomicReference<Variable>()

        setVariableAutomatically(local, variableNames, variableKeys, variableValues) { name, value, key, storage ->
//            println("$name $value ${Integer.toHexString(name.get().hashCode())} 설정됨")

            //val kValue = k.get()
            val vValue = value.get()

            when(vValue) {
                is ObjectBoolean -> {
                    val result = ObjectBoolean(vValue.value)
                    VariableStorage.setVariableWithKey(name, result, key)
                }
                is ObjectInteger -> {
                    val result = ObjectInteger(vValue.value)
                    VariableStorage.setVariableWithKey(name, result, key)
                }
                is ObjectDouble -> {
                    val result = ObjectDouble(vValue.value)
                    VariableStorage.setVariableWithKey(name, result, key)
                }
                is ObjectString -> {
                    val result = ObjectString(vValue.value)
                    VariableStorage.setVariableWithKey(name, result, key)
                }
                else -> {
                    VariableStorage.setVariableWithKey(name, vValue, key)
                }
            }
//            if(vValue is ObjectBoolean) {
//                k.set(ObjectBoolean(vValue.value))
//            }
//
//            if(vValue is ObjectChar) {
//                k.set(ObjectChar(vValue.value))
//            }
//
//            if(vValue is ObjectInteger) {
//                k.set(ObjectInteger(vValue.value))
//            }
//
//            if(vValue is ObjectDouble) {
//                k.set(ObjectDouble(vValue.value))
//            }


//
//            if((kValue is ObjectNumber || kValue is ObjectString) && (vValue is ObjectNumber || vValue is ObjectString)) {
//                println("${kValue} ${vValue} 설정중")
//                kValue.setRoot(vValue.getRoot())
//            } else {
//                k.set(v.get())
//            }
            returnVariable.set(name)


        }

        //println(returnVariable.get().get())

        return returnVariable.get()
        //        int valueIndex = 0;
        //        for(int nameIndex = 0; nameIndex < variableNames.size(); nameIndex++) {
        //            Variable variableName = variableNames.get(nameIndex);
        //            Variable variableValue = variableValues.get(valueIndex);
        //
        //            String key = (String) variableName.get();
        //            if(VariableStorage.isVariable(key)) {
        //                String name = key.substring(1, key.length() - 1);
        //                Variable variable;
        //
        //                if(local.hasVariable(name)) {
        //                    variable = local.getVariable(name);
        //                } else if(VariableStorage.GLOBAL.hasVariable(name)) {
        //                    variable = VariableStorage.GLOBAL.getVariable(name);
        //                } else {
        //                    variable = new Variable(variableValue.getMode(), false);
        //                    local.setVariable(name, variable);
        //                }
        //
        //                variable.castCompel(variableValue.getMode());
        //                variable.set(variableValue.get());
        //            } else {
        //                throw new RuntimeException("해당하는 이름은 변수 포맷이 아닙니다: " + key);
        //            }
        //            valueIndex++;
        //            if(valueIndex >= nameIndex) {
        //                valueIndex = 0;
        //            }
        //        }

        //        sentence.getMap().get("목적어").getVariables().stream().map(it -> {
        //            String val = (String)it.get();
        //            if(val.startsWith("[") && val.endsWith("]")) {
        //                return val.substring(1, val.length() - 1);
        //            } else {
        //                throw new RuntimeException("변수 포맷이 아닙니다: " + val);
        //            }
        //        }).map()
    }

//    interface LambdaInterface<K, V, R> {
//        fun accept(k: K, v: V, r: R)
//    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean {
        return true
    }
}
