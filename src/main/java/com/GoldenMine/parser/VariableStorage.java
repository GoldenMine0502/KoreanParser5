package com.GoldenMine.parser;

import com.GoldenMine.parser.predicate.Predicate설정하다;
import java.util.HashMap;
import java.util.List;

public class VariableStorage {
    public static final VariableStorage GLOBAL = createVariableStorage();

    public HashMap<String, Variable> variables = new HashMap<>();

    private VariableStorage() {

    }

    public Variable getVariable(String str) {
        return variables.get(str);
    }

    public boolean hasVariable(String str) {
        return variables.containsKey(str);
    }

    public void setVariable(String str, Variable variable) {
        variables.put(str, variable);
    }

    public static VariableStorage createVariableStorage() {
        return new VariableStorage();
    }

    @Override
    public String toString() {
        return variables.toString();
    }

    public static boolean isVariable(String key) {
        return key.startsWith("[") && key.endsWith("]");
    }

    public static void setVariableAutomatically(VariableStorage local, List<Variable> variableNames, List<Variable> variableValues, Predicate설정하다.LambdaInterface<Variable, Variable, VariableStorage> lambda) {
        int valueIndex = 0;
        for(int nameIndex = 0; nameIndex < variableNames.size(); nameIndex++) {
            Variable variableName = variableNames.get(nameIndex);
            Variable variableValue = variableValues.get(valueIndex);

            int variableNameMode = variableName.getMode().getMode();
            int variableValueMode = variableValue.getMode().getMode();

            if(variableNameMode > variableValueMode) {
                variableValue.cast(variableName.getMode());
            } else if(variableNameMode < variableValueMode) {
                variableName.cast(variableValue.getMode());
            }

            lambda.accept(variableName, variableValue, local);
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
            valueIndex++;
            if(valueIndex >= nameIndex) {
                valueIndex = 0;
            }
        }
    }
}
