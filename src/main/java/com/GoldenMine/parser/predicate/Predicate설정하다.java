package com.GoldenMine.parser.predicate;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Predicate설정하다 implements IPredicate {
    @Override
    public String getDefaultSentence() {
        return "설정하다";
    }

    @Override
    public List<String> getNeededSentenceElements() {
        return Arrays.asList("목적어", "부사어로");
    }

    @Override
    public List<String> getOptionalSentenceElements() {
        return Collections.emptyList();
    }

    @Override
    public List<String> getExceptVariableParsing() {
        return Collections.singletonList("목적어");
    }



    @Override
    public Variable perform(Sentence sentence, VariableStorage local) {

        List<Variable> variableNames = sentence.getMap().get("목적어").getVariables();
        List<Variable> variableValues = sentence.getMap().get("부사어로").getVariables();

        AtomicReference<Variable> returnVariable = new AtomicReference<>();

        VariableStorage.setVariableAutomatically(local, variableNames, variableValues, (k, v, r) -> {
            k.castCompelNoMaintain(v.getMode());
            k.set(v.get());
            returnVariable.set(k);
        });

        return returnVariable.get();
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

    public interface LambdaInterface<K, V, R> {
        void accept(K k, V v, R r);
    }
}
