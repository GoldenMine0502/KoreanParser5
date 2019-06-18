package com.GoldenMine.parser.predicate;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import com.GoldenMine.parser.predicatespecific.IMultiProcessing;
import com.GoldenMine.parser.predicatespecific.IPredicateSpecific;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Predicate반복하다 implements IPredicate, IMultiProcessing {
    @Override
    public String getDefaultSentence() {
        return "반복합니다";
    }

    @Override
    public List<String> getNeededSentenceElements() {
        return Collections.emptyList();
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

    @Override
    public List<String> getOthers() {
        return Collections.singletonList("반복문의 끝");
    }

    @Override
    public int execute(int line, Sentence sentence, MultiProcessData parameters, Variable result) {
        return 0;
    }
}
