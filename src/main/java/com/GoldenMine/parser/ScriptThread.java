package com.GoldenMine.parser;

import com.GoldenMine.parser.pronoun.IPronoun;
import com.GoldenMine.parser.pronoun.PronounStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptThread {
    private List<ParseContext> sourceCode;

    public ScriptThread(List<ParseContext> sourceCode) {
        this.sourceCode = sourceCode;
    }


    //만약 A가 B보다 크거나 같다면


    public void performAll() {
        VariableStorage LOCAL = VariableStorage.createVariableStorage();
        //LOCAL.setVariable("A", new Variable("AAAA", false));
        for (int performIndex = 0; performIndex < sourceCode.size(); performIndex++) {
            //System.out.println(performIndex + "번 실행");
            //System.out.println(LOCAL);
            ParseContext parseContext = sourceCode.get(performIndex);
            //System.out.println(parseContext.getSource());
            List<Sentence> sentences = parseContext.getSentences();
            List<PronounInfo> pronounInfoList = parseContext.getPronounInfoList();

            Sentence lastSentence = sentences.get(sentences.size() - 1);

            for(int pronounInfoIndex = 0; pronounInfoIndex < pronounInfoList.size(); pronounInfoIndex++) {
                PronounInfo info = pronounInfoList.get(pronounInfoIndex);
                applyVariable(info.getModifying(), LOCAL);
            //    info.getContext().getVariables().set(info.getVariableIndex(), info.getModifying().get서술어().perform(info.getModifying(), LOCAL));
                info.getContext().getVariables().set(info.getVariableIndex(), info.getModifiedKeyPronoun().perform(info.getModifying(), LOCAL));
            }

            applyVariable(lastSentence, LOCAL);
            lastSentence.get서술어().perform(lastSentence, LOCAL);
//            List<Variable> results = new ArrayList<>();
//
//
//
//            for (int sentenceIndex = 0; sentenceIndex < sentences.size(); sentenceIndex++) {
//
//                Sentence sentence = sentences.get(sentenceIndex);
//                //System.out.println(sentence.getMap());
//                //System.out.println(sentenceIndex + "시작");
//                applyVariable(parseContext, sentences, results, sentenceIndex, sentence, LOCAL);
//                results.add(sentence.get서술어().perform(sentence, LOCAL));
//            }
        }

        //System.out.println(LOCAL);
    }

    public void applyVariable(Sentence sentence, VariableStorage storage) {
        //List<String> except = sentence.get서술어().getExceptVariableParsing();
        //System.out.println("except: " + except);

        HashMap<String, Context> map = sentence.getMap();
        //List<Context> parseContextMap = parseContext.getParsedMap().get("서술어");
        // 대명사
        for (String key : map.keySet()) {
            Context context = map.get(key);
            List<Variable> variables = context.getVariables();

            for (int variableIndex = 0; variableIndex < variables.size(); variableIndex++) {
                Variable variable = variables.get(variableIndex);

                if (variable.getMode() == Variable.VariableMode.STRING_MODE) {
                    String value = variable.stringValue();

//                    if (PronounStorage.INSTANCE.hasPronoun(value)) {
//                        //System.out.println(value + " contain");
//                        //IPronoun pronoun = PronounStorage.INSTANCE.getPronoun(value);
//
//                        Variable result = null;
//
//                        int pos = parseContextMap.get(index).getPosStart();
//                        //System.out.println(index + ", " + pos + ", " + sentence.getMap());
//
//                        for (int sentenceIndex = index - 1; sentenceIndex >= 0; sentenceIndex--) { // 수식받을 문장 서치
//                            int currentPos = parseContextMap.get(sentenceIndex).getPosStart();
//                            //System.out.println(pos + ", " + currentPos);
//                            if (pos > currentPos) {
//                                result = results.get(sentenceIndex);
//                                //System.out.println(result);
//                                break;
//                            }
//                        }
//
//                        if (result != null) {
//                            //System.out.println("대명사 결과: " + result.get());
//                            variables.set(variableIndex, result);
////                            variable.castCompelNoMaintain(result.getMode());
////                            variable.set(result.get());
//                        } else {
//                            throw new RuntimeException("대명사이지만 수식받는 서술어가 없습니다.");
//                        }
//                    } else //if(!except.contains(key)) {
                        if (value.startsWith("[") && value.endsWith("]")) {
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
                                //System.out.println(key + ", " + valueCut);
                                variables.set(variableIndex, variableTemp);
//                                it.castCompelNoMaintain(variable.getMode());
//                                it.set(variable.get());
                            } else {
                                throw new RuntimeException(valueCut + "에 대한 변수 값이 초기화되지 않았습니다.");
                            }
                        } else {
                            StringBuilder sb = new StringBuilder(value);
                            processVariable(sb, 0, false, storage);
                            variable.castCompelNoMaintain(Variable.VariableMode.STRING_MODE);
                            variable.set(sb.toString());
                            //variable.set(variableIndex, sb.toString());
                        }
                }
                //}
            }
        }
//        for (Map.Entry<String, Context> stringContextEntry : map.entrySet()) {
//            Context stringContextEntryValue = stringContextEntry.getValue();
//            for (Variable it1 : stringContextEntryValue.getVariables()) {//Variable variable = null;
//                //System.out.println(it);
//                if (it1.getMode() == Variable.VariableMode.STRING_MODE) {
//                    String value = (String) it1.get();
//
//                    if (PronounStorage.INSTANCE.hasPronoun(value)) {
//                        //System.out.println(value + " contain");
//                        IPronoun pronoun = PronounStorage.INSTANCE.getPronoun(value);
//
//                        Variable result = null;
//
//                        //sentence.getMap();
//                        //sentence.getMap().get("서술어");
//                        int pos = parseContextMap.get(index).getPosStart();
//                        //System.out.println(index + ", " + pos + ", " + sentence.getMap());
//
//                        for (int sentenceIndex = index - 1; sentenceIndex >= 0; sentenceIndex--) {
//                            int currentPos = parseContextMap.get(sentenceIndex).getPosStart();
//                            //System.out.println(pos + ", " + currentPos);
//                            if (pos > currentPos) {
//                                result = results.get(sentenceIndex);
//                                //System.out.println(result);
//                                break;
//                            }
//                        }
//
//                        if (result != null) {
//                            //System.out.println("대명사 결과: " + result.get());
//                            it1.castCompelNoMaintain(result.getMode());
//                            it1.set(result.get());
//                        } else {
//                            throw new RuntimeException("대명사이지만 수식받는 서술어가 없습니다.");
//                        }
//                    }
//                }
//            }
//        }

//        // 변수
//        map.entrySet().stream()
//                .filter(it -> !except.contains(it.getKey()))
//                .map(Map.Entry::getValue)
//                .flatMap(it -> it.getVariables().stream())
//                .forEach(it -> {
//
//                    Variable variable = null;
//                    //System.out.println(it);
//                    if (it.getMode() == Variable.VariableMode.STRING_MODE) {
//                        String value = (String) it.get();
//
//                        //System.out.println(value);
//
//                        if (value.startsWith("[") && value.endsWith("]")) {
//                            String valueCut = value.substring(1, value.length() - 1);
//
//                            if (storage.hasVariable(valueCut)) {
//                                variable = storage.getVariable(valueCut);
//                            } else if (VariableStorage.GLOBAL.hasVariable(valueCut)) {
//                                variable = VariableStorage.GLOBAL.getVariable(valueCut);
//                            }
//
//                            if (variable != null) {
//
//                                it.castCompelNoMaintain(variable.getMode());
//                                it.set(variable.get());
//                            } else {
//                                throw new RuntimeException(valueCut + "에 대한 변수 값이 초기화되지 않았습니다.");
//                            }
//                        } else {
//                            StringBuilder sb = new StringBuilder(value);
//                            processVariable(sb, 0, false, storage);
//                            it.castCompelNoMaintain(Variable.VariableMode.STRING_MODE);
//                            it.set(sb.toString());
//                        }
//                    }
//                });
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
                //System.out.println(sb.substring(start, pos - 1));
                //System.out.println(storage.getVariable(sb.substring(start, pos - 1)).get());
            }
        }

        return pos;
    }
}
