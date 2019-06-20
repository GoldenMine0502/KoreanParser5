package com.GoldenMine.parser;

import com.GoldenMine.parser.parser.IParser;
import com.GoldenMine.parser.predicate.PredicateStorage;
import com.GoldenMine.parser.predicatespecific.IPredicateSpecific;
import com.GoldenMine.parser.pronoun.IPronoun;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;

public class ParseContext {
    //public static final Sentence DEFAULT_HASHMAP_KEY = new Sentence(null, null, null);


    public static final Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

    static {
        komoran.setFWDic("user_data/fwd.user");
        komoran.setUserDic("user_data/dic.user");
    }

    private String source;
    private HashMap<String, List<Context>> parsed = new HashMap<>();
    //private IParser parser;
    private List<Sentence> sentences = new ArrayList<>();
    private List<PronounInfo> pronounInfoList = new ArrayList<>();
    //private HashMap<Sentence, IPredicateSpecific.MultiProcessData> dataMap = new HashMap<>();
    // 연결된 대명사 문장 실행 후 마지막 문장 실행
    private boolean noParse;

    public ParseContext(String source) {
        this.source = source;

        preSourceModify();
        setNoParse();
    }

    private void preSourceModify() {
        int index = 0;

        while(source.charAt(index) == ' ' || source.charAt(index) == '\t') {
            index++;
        }
        this.source = source.substring(index);

        if(source.endsWith(".")) {
            this.source = source.substring(0, source.length() - 1);
        }
    }

    private void setNoParse() {
        //PredicateStorage.INSTANCE.getNoParses();
        if(PredicateStorage.INSTANCE.getNoParses().contains(source)) {
            noParse = true;
        }
    }

    public boolean isNoParse() {
        return noParse;
    }

    public List<PronounInfo> getPronounInfoList() {
        return pronounInfoList;
    }

    public String getSource() {
        return source;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public HashMap<String, List<Context>> getParsedMap() {
        return parsed;
    }

//    public HashMap<Sentence, IPredicateSpecific.MultiProcessData> getDataMap() {
//        return dataMap;
//    }
}
