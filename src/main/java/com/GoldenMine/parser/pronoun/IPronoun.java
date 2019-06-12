package com.GoldenMine.parser.pronoun;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;

public interface IPronoun {
    String getType();
    Variable perform(Sentence sentence, VariableStorage storage);
}
