package com.GoldenMine.parser.predicate;

import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.predicatespecific.IPredicateSpecific;
import com.GoldenMine.parser.predicatespecific.Predicate반복하다;
import com.GoldenMine.parser.predicatespecific.SpecificIF;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class PredicateStorage {
    public static final PredicateStorage INSTANCE = new PredicateStorage();
    private final HashMap<String, IPredicate> predicates = new HashMap<>();
    private final List<IPredicateSpecific> predicateSpecifics = new ArrayList<>();
    private final HashSet<String> noParses = new HashSet<>();

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
        addPredicate(new Predicate같다());
        //System.out.println("A");
        addPredicateSpecific(new SpecificIF());

        Predicate반복하다 반복하다 = new Predicate반복하다();

        addPredicate(반복하다);
        addPredicateSpecific(반복하다);
        //System.out.println("predicate add");
    }

    public void addPredicate(IPredicate predicate) {
        predicates.put(ParseContext.komoran.analyze(predicate.getDefaultSentence()).getList().get(0).getFirst(), predicate);
    }

    //    public void addPredicateMulti(IMultiProcessing predicate) {
//        predicateMultis.add(predicate);
//    }
    public void addPredicateSpecific(IPredicateSpecific specific) {
        predicateSpecifics.add(specific);
        noParses.addAll(specific.getOthers());
    }
//
//    public List<? extends IMultiProcessing> getMultis() {
//        return predicateMultis;
//    }

    public List<? extends IPredicateSpecific> getSpecifics() {
        return predicateSpecifics;
    }

    public IPredicate getPredicate(String str) {
        //System.out.println(ParseContext.komoran.analyze(str).getList().get(0).getFirst());
        return predicates.get(getRoot(str));
    }

    public static String getRoot(String str) {
        return ParseContext.komoran.analyze(str).getList().get(0).getFirst();
    }

    public HashSet<? extends String> getNoParses() {
        return noParses;
    }
}
