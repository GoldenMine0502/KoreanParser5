package com.GoldenMine.parser.predicate;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import java.util.List;

public interface IPredicate {
    String getDefaultSentence();

    List<String> getNeededSentenceElements();

    List<String> getOptionalSentenceElements();

    @Deprecated
    List<String> getExceptVariableParsing();

    Variable perform(Sentence sentence, VariableStorage local);

//    List<VariableVerify> preInterpret(KoreanProgram program, Sentence sentence, HashMap<String, List<Element>> parameters);
//
//    VariableResult execute(KoreanProgram program, Sentence sentence, HashMap<String, List<Element>> parameters);

}
