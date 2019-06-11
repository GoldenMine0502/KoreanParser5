package com.GoldenMine.parser.predicate;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Predicate곱하다 implements IPredicate {
    @Override
    public String getDefaultSentence() {
        return "곱하다";
    }

    @Override
    public List<String> getNeededSentenceElements() {
        return Arrays.asList("목적어", "부사어에");
    }

    @Override
    public List<String> getOptionalSentenceElements() {
        return Collections.emptyList();
    }

    @Override
    public List<String> getExceptVariableParsing() {
        return Collections.singletonList("부사어에");
    }

    @Override
    public Variable perform(Sentence sentence, VariableStorage local) {
        List<Variable> variableNames = sentence.getMap().get("부사어에").getVariables();
        List<Variable> variableValues = sentence.getMap().get("목적어").getVariables();

        AtomicReference<Variable> returnVariable = new AtomicReference<>();

        VariableStorage.setVariableAutomatically(local, variableNames, variableValues, (k, v, r) -> {
            //System.out.println(k.getMode() + ", " + k.get());
            //System.out.println(v.getMode() + ", " + v.get());
            if(k.getMode().getMode() > v.getMode().getMode()) {
                v.cast(k.getMode());
            } else if(k.getMode().getMode() < v.getMode().getMode()) {
                k.cast(v.getMode());
            }
            switch(k.getMode()) {
                case BOOLEAN_MODE:
                    throw new RuntimeException("Boolean은 곱할 수 없습니다.");
                case INT_MODE:
                    k.set((long)k.get() * (long)v.get());
                    break;
                case REALNUM_MODE:
                    k.set((double)k.get() * (double)v.get());
                    break;
                case STRING_MODE:
                    throw new RuntimeException("String은 곱할 수 없습니다.");
            }
            returnVariable.set(k);
        });

        return returnVariable.get();
    }
}
