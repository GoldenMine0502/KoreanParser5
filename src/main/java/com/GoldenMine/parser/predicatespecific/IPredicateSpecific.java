package com.GoldenMine.parser.predicatespecific;

import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.Variable;
import com.GoldenMine.parser.VariableStorage;
import java.util.ArrayList;
import java.util.List;
import kr.co.shineware.nlp.komoran.model.KomoranResult;

public interface IPredicateSpecific {
    boolean verify(String source, KomoranResult result);

    Variable perform(Sentence sentence, VariableStorage local, Variable originalResult);

    List<String> getOthers();

    int execute(int line, Sentence sentence, MultiProcessData parameters, Variable result);

    class MultiProcessData {
        private List<Integer> applyLine;
        private Object meta;

        public MultiProcessData(List<Integer> applyLine) {
            this.applyLine = applyLine;
        }

        public int getAppliedValue(int index) {
            return applyLine.get(index);
        }

        public int getSize() {
            return applyLine.size();
        }

        public Object getMeta() {
            return meta;
        }

        public void setMeta(Object meta) {
            this.meta = meta;
        }
    }
}
