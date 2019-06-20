package com.GoldenMine.parser;

import com.GoldenMine.parser.predicate.IPredicate;
import com.GoldenMine.parser.predicatespecific.IPredicateSpecific;
import java.util.HashMap;

public class Sentence {
    // IPredicate, IPredicateSpecific는 Sentence 클래스와 상호 참조중.
    // 논리적 문제인지 설계상 어쩔 수 없는 지는 확인해야 함.

    private HashMap<String, Context> map;
    private IPredicate 서술어;
    private IPredicateSpecific.MultiProcessData multiProcessData;
    private IPredicateSpecific specific;

    private boolean isLast;

    public Sentence(HashMap<String, Context> map, IPredicate 서술어, IPredicateSpecific specific) {
        this.map = map;
        this.서술어 = 서술어;
        this.specific = specific;
    }

    public void setMultiProcessData(IPredicateSpecific.MultiProcessData data) {
        this.multiProcessData = data;
    }

    public void setLast(boolean last) {
        this.isLast = last;
    }

    public IPredicate get서술어() {
        return 서술어;
    }

    public IPredicateSpecific.MultiProcessData getMultiProcessData() {
        return multiProcessData;
    }

    public IPredicateSpecific getSpecific() {
        return specific;
    }

    public HashMap<String, Context> getMap() {
        return map;
    }

    public boolean isLast() {
        return isLast;
    }

    @Override
    public String toString() {
        return 서술어.getDefaultSentence() + ": " + map;
    }
}
