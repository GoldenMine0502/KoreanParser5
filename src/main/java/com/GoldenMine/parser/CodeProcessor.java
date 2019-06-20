package com.GoldenMine.parser;

import com.GoldenMine.parser.parser.IParser;
import com.GoldenMine.parser.predicate.PredicateStorage;
import com.GoldenMine.parser.predicatespecific.IPredicateSpecific;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CodeProcessor {
//    private static final HashSet<String> exceptCodes = new HashSet<>();
//
//    static {
//        for (IPredicateSpecific specific : PredicateStorage.INSTANCE.getSpecifics()) {
//            exceptCodes.addAll(specific.getOthers());
//        }
//    }

    private Code code;
    private boolean debug;

    public CodeProcessor(Code code) {
        this.code = code;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void compile(IParser parser) {
        if(debug) {
            System.out.println("compiling...");
        }

        List<ParseContext> sourceCode = code.getSourceCode();
        for (int line = 0; line < sourceCode.size(); line++) {
            parser.parse(code, sourceCode.get(line), line);
            if(debug) {
                System.out.println("compiled line " + line + ": " + sourceCode.get(line).getParsedMap());
            }
        }

        if(debug) {
            System.out.println("compiled. ready to perform");
        }
    }

    public void perform() {
        List<ParseContext> sourceCode = code.getSourceCode();
        VariableStorage LOCAL = VariableStorage.createVariableStorage();

        for (int performIndex = 0; performIndex < sourceCode.size(); performIndex++) {
            try {
                ParseContext parseContext = sourceCode.get(performIndex);

                if (parseContext.isNoParse()) {
                    Sentence sentence = parseContext.getSentences().get(0);
                    performIndex = sentence.getSpecific().execute(performIndex, sentence, sentence.getMultiProcessData(), null);
                } else {
                    List<Sentence> sentences = parseContext.getSentences();
                    List<PronounInfo> pronounInfoList = parseContext.getPronounInfoList();
                    Sentence lastSentence = sentences.get(sentences.size() - 1);

                    for (int pronounInfoIndex = 0; pronounInfoIndex < pronounInfoList.size(); pronounInfoIndex++) {
                        PronounInfo info = pronounInfoList.get(pronounInfoIndex);
                        applyVariable(info.getModifying(), LOCAL);

                        Sentence modified = info.getModified();
                        Variable result = info.getModifiedKeyPronoun().perform(info.getModifying(), LOCAL);
                        info.getContext().getVariables().set(info.getVariableIndex(), result);

                        if (modified.getSpecific() != null) {
                            performIndex = modified.getSpecific().execute(performIndex, modified, modified.getMultiProcessData(), result);
                        }
                    }

                    applyVariable(lastSentence, LOCAL);
                    Variable result = lastSentence.get서술어().perform(lastSentence, LOCAL);

                    if (lastSentence.getSpecific() != null) {
                        performIndex = lastSentence.getSpecific().execute(performIndex, lastSentence, lastSentence.getMultiProcessData(), result);
                    }
                }
            } catch(Exception ex) {
                System.out.println("line " + performIndex + "에서 에러발생");
                ex.printStackTrace();
                break;
            }
        }
    }

    public void applyVariable(Sentence sentence, VariableStorage storage) {
        HashMap<String, Context> map = sentence.getMap();

        for (String key : map.keySet()) {
            Context context = map.get(key);
            List<Variable> variables = context.getVariables();

            for (int variableIndex = 0; variableIndex < variables.size(); variableIndex++) {
                Variable variable = variables.get(variableIndex);

                if (variable.getMode() == Variable.VariableMode.STRING_MODE) {
                    String value = variable.stringValue();

                    if (VariableStorage.isVariable(value)) {
                        String valueCut = value.substring(1, value.length() - 1);

                        Variable variableTemp;

                        if (storage.hasVariable(valueCut)) {
                            variableTemp = storage.getVariable(valueCut);
                        } else if (VariableStorage.GLOBAL.hasVariable(valueCut)) {
                            variableTemp = VariableStorage.GLOBAL.getVariable(valueCut);
                        } else {
                            variableTemp = new Variable(0, false);
                            storage.setVariable(valueCut, variableTemp);
                        }

                        if (variableTemp != null) {
                            variables.set(variableIndex, variableTemp);
                        } else {
                            throw new RuntimeException(valueCut + "에 대한 변수 값이 초기화되지 않았습니다.");
                        }
                    } else {
                        StringBuilder sb = new StringBuilder(value);
                        processVariable(sb, 0, false, storage);
                        variable.castCompelNoMaintain(Variable.VariableMode.STRING_MODE);
                        variable.set(sb.toString());
                    }
                }
            }
        }
    }

    public static int processVariable(StringBuilder sb, int pos, boolean inner, VariableStorage storage) {
        int start = pos;

        while (pos < sb.length()) {
            char ch = sb.charAt(pos);
            ++pos;
            if (ch == '[')
                pos = processVariable(sb, pos, true, storage);
            else if (ch == ']')
                break;
        }

        if (inner) {
            try {
                String key = sb.substring(start, pos - 1);
                Variable val = storage.getVariable(key);

                if (val != null) {
                    sb.delete(start - 1, pos);

                    String valToStr = String.valueOf(val.get());
                    sb.insert(start - 1, valToStr);
                    pos = start - 1 + valToStr.length();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return pos;
    }
}
