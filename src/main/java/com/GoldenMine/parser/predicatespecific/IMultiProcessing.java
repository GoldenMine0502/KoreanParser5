package com.GoldenMine.parser.predicatespecific;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import java.util.ArrayList;
import java.util.List;

public interface IMultiProcessing {
    List<String> getOthers();
    int execute(int line, Sentence sentence, MultiProcessData parameters, Variable result);

    class MultiProcessData {
        List<Integer> applyLine = new ArrayList<>();
    }
}
