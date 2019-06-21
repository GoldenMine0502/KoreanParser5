package com.GoldenMine.parser.predicatespecific;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import java.util.Arrays;
import java.util.List;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.util.common.model.Pair;

public class SpecificIF implements IPredicateSpecific {

    @Override
    public List<String> getOthers() {
        return Arrays.asList("그렇지 않으면", "조건문의 끝");
    }

    @Override
    public int execute(int line, Sentence sentence, MultiProcessData parameters, List<Variable> results) {
        if(line == parameters.getAppliedValue(0)) {
            Variable result = results.get(results.size() - 1);

            boolean istrue = result.isTrue();
            parameters.setMeta(istrue);

            if (istrue) {
                return line;
            } else {
                int elseParam = parameters.getAppliedValue(1);
                if (elseParam != -1) {
                    return elseParam;
                } else {
                    return parameters.getAppliedValue(2);
                }
            }
        } else if(line == parameters.getAppliedValue(1)){
            boolean istrue = (boolean) parameters.getMeta();

            if(istrue) {
                return parameters.getAppliedValue(2);
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
    public Variable perform(Sentence sentence, VariableStorage local, Variable originalResult) {
        return originalResult;
    }
}
