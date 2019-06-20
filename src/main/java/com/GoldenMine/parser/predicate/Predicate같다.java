package com.GoldenMine.parser.predicate;

import com.GoldenMine.parser.Context;
import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import com.GoldenMine.parser.predicatespecific.IPredicateSpecific;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Predicate같다 implements IPredicate {
    @Override
    public String getDefaultSentence() {
        return "같다";
    }

    @Override
    public List<String> getNeededSentenceElements() {
        return Collections.singletonList("주어");
    }

    @Override
    public List<String> getOptionalSentenceElements() {
        return Collections.singletonList("보어");
    }

    @Override
    public List<String> getExceptVariableParsing() {
        return Collections.emptyList();
    }

    @Override
    public Variable perform(Sentence sentence, VariableStorage local) {
        Context 주어 = sentence.getMap().get("주어");
        Context 보어 = sentence.getMap().get("보어");

        //System.out.println(주어);

        List<Variable> variables1 = 주어.getVariables();
        Variable first = variables1.get(0);

        boolean returnValue = true;

        for(int i = 1; i < variables1.size(); i++) {
            if(!first.equals(variables1.get(i))) {
                returnValue = false;
                break;
            }
        }

        if(returnValue && 보어 != null) {
            List<Variable> variables2 = 보어.getVariables();
            for (int i = 0; i < variables2.size(); i++) {
                if(!first.equals(variables2.get(i))) {
                    returnValue = false;
                    break;
                }
            }
        }
        return new Variable(returnValue, false);
    }
}
