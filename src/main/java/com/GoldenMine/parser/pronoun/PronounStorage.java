package com.GoldenMine.parser.pronoun;

import java.util.HashMap;

public class PronounStorage {
    public static final PronounStorage INSTANCE = new PronounStorage();

    private HashMap<String, IPronoun> pronouns = new HashMap<>();

    private PronounStorage() {
        init();
    }

    private void init() {
        addPronoun(new IPronoun것());
    }

    public IPronoun getPronoun(String key) {
        return pronouns.get(key);
    }

    public boolean hasPronoun(String key) {
        return pronouns.containsKey(key);
    }

    public void addPronoun(IPronoun pronoun) {
        pronouns.put(pronoun.getType(), pronoun);
    }
}
