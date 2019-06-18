package com.GoldenMine.parser.predicatespecific;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import kr.co.shineware.nlp.komoran.model.KomoranResult;

public interface IPredicateSpecific {
    boolean verify(String source, KomoranResult result);

    Variable perform(Sentence sentence, VariableStorage local);
}
