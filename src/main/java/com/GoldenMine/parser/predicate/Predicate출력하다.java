package com.GoldenMine.parser.predicate;

import com.GoldenMine.parser.Context;
import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Predicate출력하다 implements IPredicate {
    @Override
    public String getDefaultSentence() {
        return "출력하다";
    }

    @Override
    public List<String> getNeededSentenceElements() {
        return Collections.singletonList("목적어");
    }

    @Override
    public List<String> getOptionalSentenceElements() {
        return Collections.emptyList();
    }

    @Override
    public List<String> getExceptVariableParsing() {
        return Collections.emptyList();
    }

    @Override
    public Variable perform(Sentence sentence, VariableStorage local) {
        HashMap<String, Context> map = sentence.getMap();
        List<Variable> 목적어 = map.get("목적어").getVariables();
        for(int i = 0; i < 목적어.size(); i++) {
            System.out.println(목적어.get(i).get());
        }
        if(목적어.size() == 1) {
            return 목적어.get(0);
        } else if(목적어.size() > 1){
            StringBuilder sb = new StringBuilder();
            sb.append(목적어.get(0).get());
            for(int i = 1; i < 목적어.size(); i++) {
                sb.append("\n");
                sb.append(목적어.get(i).get());
            }

            return new Variable(sb.toString(), false);
        } else {
            return null;
        }
    }
}
