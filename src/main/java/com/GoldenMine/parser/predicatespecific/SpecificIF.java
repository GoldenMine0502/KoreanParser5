package com.GoldenMine.parser.predicatespecific;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import java.util.Arrays;
import java.util.List;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.util.common.model.Pair;

public class SpecificIF implements IPredicateSpecific, IMultiProcessing {
    @Override
    public List<String> getOthers() {
        return Arrays.asList("그렇지 않으면", "조건문의 끝");
    }

    @Override
    public int execute(int line, Sentence sentence, MultiProcessData parameters, Variable result) {
        if (parameters.applyLine.size() == 2) {
            if (result.isTrue()) {
                return line;
            } else {
                return parameters.applyLine.get(1);
            }
        } else if (parameters.applyLine.size() == 3) {
            if (result.isTrue()) {
                return line;
            } else {
                return parameters.applyLine.get(1);
            }
        }
        return line;
    }

    @Override
    public boolean verify(String source, KomoranResult result) {
        List<Pair<String, String>> list = result.getList();
        Pair<String, String> last = list.get(list.size() - 1);

        switch (last.getFirst()) {
            case "라면":
            case "ㄴ다면":
            case "다면":
            case "자면":
            case "면":
            case "으면":
                return true;
        }

        return false;
    }

    @Override
    public Variable perform(Sentence sentence, VariableStorage local) {
        return null;
    }
}
