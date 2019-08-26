package kr.goldenmine.parser

import kr.goldenmine.objects.Variable
import java.util.*

class VariableStorage private constructor(name: String) {
    val name: String = name

    var variables = HashMap<String, Variable>()

    fun getVariable(str: String): Variable? {
        return variables[str]
    }

    fun hasVariable(str: String): Boolean {
        return variables.containsKey(str)
    }

    fun setVariable(str: String, variable: Variable) {
        variables[str] = variable
    }

    override fun toString(): String {
        return variables.toString()
    }

    fun removeVariable(str: String) {
        variables.remove(str)
    }

    companion object {
        val GLOBAL = createVariableStorage("main")

        fun createVariableStorage(name: String): VariableStorage {
            return VariableStorage(name)
        }

        fun isVariable(key: String): Boolean {
            //        if(key.startsWith("[")) {
            //
            //        }

            var depth = 0
            var started = false
            for(ch in key) {
                if(ch == '[') depth++; started = true
                if(ch == ']') depth--
                if(depth < 0) return false
            }

            return key.startsWith('[') && key.indexOf(']') == key.length-1 && started && depth == 0

//            if(key.startsWith('[') && key.endsWith(']')) {
//                var viewed = false
//
//                for(i in 1 until key.length-1) {
//                    val ch = key[i]
//                    if(ch == ' ') {
//                        return false
//                    }
//                }
//                return true
//            }
//
//            return false
            //return key.startsWith("[") && key.endsWith("]");
        }

        fun setVariableAutomatically(local: VariableStorage, variableNames: List<Variable?>, variableValues: List<Variable?>, lambda: (Variable, Variable, VariableStorage) -> Unit) {
            var valueIndex = 0

//            val variableNames = ArrayList<Variable?>(variableNamesOriginal.size)
//            variableNamesOriginal.forEach { if(it != null) variableNames.add(Variable(it)) else variableNames.add(null) }

            for (nameIndex in variableNames.indices) {
                val variableName = variableNames[nameIndex]
                val variableValue = variableValues[valueIndex]

                val variableNameMode = variableName!!.mode.mode
                val variableValueMode = variableValue!!.mode.mode

                if (variableNameMode > variableValueMode) {
                    variableValue.cast(variableName.mode)
                } else if (variableNameMode < variableValueMode) {
                    variableName.cast(variableValue.mode)
                }

                lambda.invoke(variableName, variableValue, local)
                //            String key = (String) variableName.get();
                //            if(VariableStorage.isVariable(key)) {
                //                String name = key.substring(1, key.length() - 1);
                //                Variable variable;
                //                if(local.hasVariable(name)) {
                //                    variable = local.getVariable(name);
                //                } else if(VariableStorage.GLOBAL.hasVariable(name)) {
                //                    variable = VariableStorage.GLOBAL.getVariable(name);
                //                } else {
                //                    variable = new Variable(variableValue.getMode(), false);
                //                    local.setVariable(name, variable);
                //                }
                //
                //
                ////                variable.castCompel(variableValue.getMode());
                ////                variable.set(variableValue.get());
                //            } else {
                //                throw new RuntimeException("해당하는 이름은 변수 포맷이 아닙니다: " + key);
                //            }
                valueIndex++
                if (valueIndex >= variableValues.size) {
                    valueIndex = 0
                }
            }
        }
    }
}
