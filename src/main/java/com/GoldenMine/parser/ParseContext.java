package com.GoldenMine.parser;

import com.GoldenMine.parser.parser.IParser;
import com.GoldenMine.parser.pronoun.IPronoun;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParseContext {
    private String source;
    private HashMap<String, List<Context>> parsed = new HashMap<>();
    private IParser parser;
    private List<Sentence> sentences = new ArrayList<>();
    private List<PronounInfo> pronounInfoList = new ArrayList<>();

    // 연결된 대명사 문장 실행 후 마지막 문장 실행

    public ParseContext(String source) {
        if(source.endsWith(".")) {
            this.source = source.substring(0, source.length() - 1);
        } else {
            this.source = source;
        }
    }

    public void setParser(IParser parser) {
        this.parser = parser;
    }

    public void parse() {
        parsed = new HashMap<>();

        parser.parse(this);
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

    public IParser getParser() {
        return parser;
    }
}
