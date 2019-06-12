package com.GoldenMine.parser.pronoun;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;

public class IPronoun것 implements IPronoun {

    @Override
    public String getType() {
        return "것";
    }

    @Override
    public Variable perform(Sentence sentence, VariableStorage storage) {
        return sentence.get서술어().perform(sentence, storage);
    }
}
