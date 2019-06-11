package com.GoldenMine.parser;

import com.GoldenMine.parser.predicate.IPredicate;
import com.GoldenMine.parser.pronoun.PronounStorage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sentence {
    private HashMap<String, Context> map;
    private IPredicate 서술어;

    public Sentence(HashMap<String, Context> map, IPredicate 서술어) {
        this.map = map;
        this.서술어 = 서술어;
    }

    public IPredicate get서술어() {
        return 서술어;
    }

    public HashMap<String, Context> getMap() {
        return map;
    }

    @Override
    public String toString() {
        return 서술어.getDefaultSentence() + ": " + map;
    }


}
