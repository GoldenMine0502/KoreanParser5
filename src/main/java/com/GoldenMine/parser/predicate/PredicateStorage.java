package com.GoldenMine.parser.predicate;

import java.util.HashMap;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;

public class PredicateStorage {
    public static final PredicateStorage INSTANCE = new PredicateStorage();
    private HashMap<String, IPredicate> predicates = new HashMap<>();
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

    public IPredicate getPredicate(String str) {
        return predicates.get(komoran.analyze(str).getList().get(0).getFirst());
    }
}
