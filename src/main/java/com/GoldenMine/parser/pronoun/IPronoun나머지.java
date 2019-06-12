package com.GoldenMine.parser.pronoun;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import com.GoldenMine.parser.predicate.PredicateStorage;
import com.GoldenMine.parser.predicate.Predicate나누다;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class IPronoun나머지 implements IPronoun {
    private Predicate나누다 key = (Predicate나누다) PredicateStorage.INSTANCE.getPredicate("나누다");

    @Override
    public String getType() {
        return "나머지";
    }

    @Override
    public Variable perform(Sentence sentence, VariableStorage storage) {

        if(sentence.get서술어() == key) {
            List<Variable> variableNames = sentence.getMap().get("목적어").getVariables();
            List<Variable> variableValues = sentence.getMap().get("부사어로").getVariables();

            AtomicReference<Variable> returnVariable = new AtomicReference<>();

            VariableStorage.setVariableAutomatically(storage, variableNames, variableValues, (k, v, r) -> {
                switch(k.getMode()) {
                    case BOOLEAN_MODE:
                        throw new RuntimeException("Boolean은 나눌 수 없습니다.");
                    case INT_MODE:
                        k.set(k.intValue() % v.intValue());
                        break;
                    case REALNUM_MODE:
                        k.set(k.realNumValue() % v.realNumValue());
                        break;
                    case STRING_MODE:
                        throw new RuntimeException("String은 나눌 수 없습니다.");
                }
                returnVariable.set(k);
            });

            return returnVariable.get();
        } else {
            return sentence.get서술어().perform(sentence, storage);
        }
    }
}
