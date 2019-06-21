package com.GoldenMine.parser.predicatespecific;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import com.GoldenMine.parser.predicate.IPredicate;
import com.GoldenMine.parser.predicate.PredicateStorage;
import com.GoldenMine.parser.predicatespecific.IPredicateSpecific;
import java.util.Collections;
import java.util.List;
import kr.co.shineware.nlp.komoran.model.KomoranResult;

public class Predicate반복하다 implements IPredicate, IPredicateSpecific{
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
    public Variable perform(Sentence sentence, VariableStorage local) { // void 메소드
        return null;
    }

    @Override
    public boolean verify(String source, KomoranResult result) {
        return result.getList().get(0).getFirst().equals(PredicateStorage.getRoot(getDefaultSentence()));
    }

    @Override
    public Variable perform(Sentence sentence, VariableStorage local, Variable originalResult) {
        return originalResult;
    }
    @Override
    public List<String> getOthers() {
        return Collections.singletonList("반복문의 끝");
    }

    @Override
    public int execute(int line, Sentence sentence, IPredicateSpecific.MultiProcessData parameters, List<Variable> results) {
        if(line == parameters.getAppliedValue(0)) {
            Variable result = results.get(results.size() - 1);

            if(!result.isTrue()) {
                return parameters.getAppliedValue(1);
            }
        } else if(line == parameters.getAppliedValue(1)){
            return parameters.getAppliedValue(0) - 1;
        }
        return line;
    }
}
