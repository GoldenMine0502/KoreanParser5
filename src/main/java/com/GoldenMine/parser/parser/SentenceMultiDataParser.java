package com.GoldenMine.parser.parser;

import com.GoldenMine.parser.Code;
import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.predicatespecific.IPredicateSpecific;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SentenceMultiDataParser implements IParser {
    private IParser parser;

    public SentenceMultiDataParser(IParser parser) {
        this.parser = parser;
    }

    @Override
    public void parse(Code code, ParseContext context, int index) {
        if (parser != null)
            parser.parse(code, context, index);
        if(!context.isNoParse()) {
            List<Sentence> sentences = context.getSentences();

            //for (int sentenceIndex = ; sentenceIndex < sentences.size(); sentenceIndex++)
            {
                Sentence sentence = sentences.get(sentences.size() - 1);
                IPredicateSpecific specific = sentence.getSpecific();


                if (specific != null) {
                    List<String> others = specific.getOthers();
                    System.out.println("not null specific " + index + ", " + others.size());

                    int overlap = 0;
                    Integer[] multiIndex = new Integer[others.size() + 1];
                    multiIndex[0] = index;
                    for (int i = 1; i < multiIndex.length; i++) {
                        multiIndex[i] = -1;
                    }
                    int set = 0;

                    for (int line = index + 1; line < code.getSourceCode().size(); line++) {
                        ParseContext parseContext = code.getSourceCode().get(line);
                        List<Sentence> innerSentences = parseContext.getSentences();
                        String source = parseContext.getSource();

                        for (int innerSentenceIndex = 0; innerSentenceIndex < innerSentences.size(); innerSentenceIndex++) {
                            Sentence innerSentence = innerSentences.get(innerSentenceIndex);

                            if (specific == innerSentence.getSpecific()) {
                                overlap++;
                            }
                        }
                        if(parseContext.isNoParse()) {
                            int containsSource = others.indexOf(source);
                            System.out.println(source + ", " + others + ", " + containsSource);
                            if (containsSource >= 0) {
                                if (overlap == 0) {
                                    multiIndex[containsSource + 1] = line;
                                    set++;
                                    if (set >= others.size()) {
                                        break;
                                    }
                                } else {
                                    if (containsSource == others.size() - 1) {
                                        overlap--;
                                    }
                                }
                            }
                        }

                        System.out.println(line + ", " + overlap);
                    }

                    //System.out.println(multiIndex[0] + ", " + multiIndex[1]);

                    IPredicateSpecific.MultiProcessData data = new IPredicateSpecific.MultiProcessData(Arrays.asList(multiIndex));

                    sentence.setMultiProcessData(data);
                    for (int i = 1; i < multiIndex.length; i++) {
                        //System.out.println(multiIndex[i]==null);
                        int value = multiIndex[i];
                        if (value != -1) {
                            ParseContext secondary = code.getSourceCode().get(value);
//                            List<Sentence> secSentences = secondary.getSentences();
//
//                            for(int senIndex = 0; senIndex < secSentences.size(); senIndex++) {
//                                Sentence secSentence = secSentences.get(senIndex);
//
//                            }
                            if(secondary.isNoParse()) {
                                Sentence defaultSentence = new Sentence(null, sentence.get서술어(), sentence.getSpecific());
                                defaultSentence.setMultiProcessData(data);
                                secondary.getSentences().add(defaultSentence);
                            }
                        }
                    }
                }
            }
        }
    }
}
