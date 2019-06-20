package com.GoldenMine.parser.parser;

import com.GoldenMine.parser.*;
import com.GoldenMine.parser.postposition.IPostPosition;
import com.GoldenMine.parser.pronoun.PronounStorage;
import java.util.HashMap;
import java.util.List;

public class PronounParser implements IParser {
    private IParser parser;


    public PronounParser(IParser parser) {
        this.parser = parser;
    }

    @Override
    public void parse(Code code, ParseContext parseContext, int index) {
        if (parser != null)
            parser.parse(code, parseContext, index);

        if(!parseContext.isNoParse()) {

            List<Context> predicates = parseContext.getParsedMap().get("서술어");
            List<Sentence> sentences = parseContext.getSentences();

            for (int sentenceIndex = 0; sentenceIndex < sentences.size(); sentenceIndex++) {
                Sentence sentence = sentences.get(sentenceIndex);
                int sentencePos = predicates.get(sentenceIndex).getPosStart();

                HashMap<String, Context> map = sentence.getMap();
                for (String key : map.keySet()) {
                    if (!key.equals("서술어")) {
                        Context context = map.get(key);
                        for (int variableIndex = 0; variableIndex < context.getVariables().size(); variableIndex++) {
                            Variable variable = context.getVariables().get(variableIndex);
                            if (variable.getMode() == Variable.VariableMode.STRING_MODE) {
                                String modifiedKey = variable.stringValue();

                                if (PronounStorage.INSTANCE.hasPronoun(variable.stringValue())) {
                                    Sentence modifying = null;
                                    int modifyingIndex = 0;

                                    for (int modifyingIndexFor = sentenceIndex - 1; modifyingIndexFor >= 0; modifyingIndexFor--) {
                                        Sentence modifyingFor = sentences.get(modifyingIndexFor);
                                        int modifyingForIndex = predicates.get(modifyingIndexFor).getPosStart();

                                        if (sentencePos > modifyingForIndex) {
                                            modifying = modifyingFor;
                                            modifyingIndex = modifyingForIndex;
                                            break;
                                        }
                                    }

                                    if (modifying != null) {
                                        PronounInfo info = new PronounInfo(modifying, modifyingIndex, sentence, sentenceIndex, context, variableIndex, modifiedKey, PronounStorage.INSTANCE.getPronoun(modifiedKey));
                                        parseContext.getPronounInfoList().add(info);
                                    } else {
                                        throw new RuntimeException("대명사이지만 수식받는 서술어가 없습니다.");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    } // [A]에 100을 더한 것과 [B]에 3을 더한 것에 3과 4를 더합니다
}
