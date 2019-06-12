package com.GoldenMine.parser.predicate;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Predicate나누다 implements IPredicate {
    @Override
    public String getDefaultSentence() {
        return "나누다";
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
            //System.out.println(k + ", " + v);
            switch(k.getMode()) {
                case BOOLEAN_MODE:
                    throw new RuntimeException("Boolean은 나눌 수 없습니다.");
                case INT_MODE:
                    k.set(k.intValue() / v.intValue());
                    break;
                case REALNUM_MODE:
                    k.set(k.realNumValue() / v.realNumValue());
                    break;
                case STRING_MODE:
                    throw new RuntimeException("String은 나눌 수 없습니다.");
            }
            returnVariable.set(k);
        });

        return returnVariable.get();
    }
}
