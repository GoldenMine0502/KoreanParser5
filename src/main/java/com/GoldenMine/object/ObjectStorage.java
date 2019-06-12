package com.GoldenMine.object;

import java.util.HashMap;

public class ObjectStorage {
    public static final ObjectStorage INSTANCE = new ObjectStorage();

    private HashMap<String, KoreanObjectCreator> creators = new HashMap<>();

    private ObjectStorage() {
        init();
    }

    private void init() {

    }

    public void addCreator(KoreanObjectCreator creator) {
        creators.put(creator.getType(), creator);
    }
}
