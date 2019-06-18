package com.GoldenMine.parser.predicate;

import com.GoldenMine.parser.predicatespecific.IMultiProcessing;
import com.GoldenMine.parser.predicatespecific.IPredicateSpecific;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;

public class PredicateStorage {
    public static final PredicateStorage INSTANCE = new PredicateStorage();
    private HashMap<String, IPredicate> predicates = new HashMap<>();
    private List<IPredicateSpecific> predicateSpecifics = new ArrayList<>();
    private List<IMultiProcessing> predicateMultis = new ArrayList<>();
    private Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

    private PredicateStorage() {
        init();
    }

    private void init() {
        addPredicate(new Predicate출력하다());
        addPredicate(new Predicate설정하다());
        addPredicate(new Predicate더하다());
        addPredicate(new Predicate빼다());
        addPredicate(new Predicate곱하다());
        addPredicate(new Predicate나누다());
    }

    public void addPredicate(IPredicate predicate) {
        predicates.put(komoran.analyze(predicate.getDefaultSentence()).getList().get(0).getFirst(), predicate);
    }

    public void addPredicateMulti(IMultiProcessing predicate) {
        predicateMultis.add(predicate);
    }
    public void addPredicateSpecific(IPredicateSpecific predicate) {
        predicateSpecifics.add(predicate);
    }

    public List<? extends IMultiProcessing> getMultis() {
        return predicateMultis;
    }

    public List<? extends IPredicateSpecific> getSpecifics() {
        return predicateSpecifics;
    }

    public IPredicate getPredicate(String str) {
        return predicates.get(komoran.analyze(str).getList().get(0).getFirst());
    }
}
