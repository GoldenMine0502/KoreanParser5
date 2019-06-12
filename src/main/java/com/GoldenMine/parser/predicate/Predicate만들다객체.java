package com.GoldenMine.parser.predicate;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Predicate만들다객체 implements IPredicate {
    @Override
    public String getDefaultSentence() {
        return "만들다";
    }

    @Override
    public List<String> getNeededSentenceElements() {
        return Arrays.asList("부사어로", "목적어");
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

        return null;
    }
}
