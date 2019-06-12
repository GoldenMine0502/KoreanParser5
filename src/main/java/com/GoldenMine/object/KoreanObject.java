package com.GoldenMine.object;

import com.GoldenMine.parser.Variable;

public interface KoreanObject extends Comparable<KoreanObject> {
    Object getValue(String key);
    void setValue(String key, Object value);
    void setValue(int index, Object value);
}
