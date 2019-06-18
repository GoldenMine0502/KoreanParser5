package com.GoldenMine.parser;

import com.GoldenMine.parser.predicate.IPredicate;
import com.GoldenMine.parser.predicatespecific.IMultiProcessing;
import com.GoldenMine.parser.predicatespecific.IPredicateSpecific;
import com.GoldenMine.parser.pronoun.PronounStorage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sentence {
    private HashMap<String, Context> map;
    private IPredicate 서술어;
    private IMultiProcessing multiProcessing;
    private IMultiProcessing.MultiProcessData multiProcessData;
    private IPredicateSpecific specific;

    private boolean isLast;

    public Sentence(HashMap<String, Context> map, IPredicate 서술어, IMultiProcessing processing, IMultiProcessing.MultiProcessData data, IPredicateSpecific specific, boolean isLast) {
        this.map = map;
        this.서술어 = 서술어;
        this.multiProcessing = processing;
        this.multiProcessData = data;
        this.specific = specific;
        this.isLast = isLast;
    }

    public IPredicate get서술어() {
        return 서술어;
    }

    public IMultiProcessing getMultiProcessing() {
        return multiProcessing;
    }

    public IMultiProcessing.MultiProcessData getMultiProcessData() {
        return multiProcessData;
    }

    public IPredicateSpecific getSpecific() {
        return specific;
    }

    public HashMap<String, Context> getMap() {
        return map;
    }

    @Override
    public String toString() {
        return 서술어.getDefaultSentence() + ": " + map;
    }


}
