package com.GoldenMine.object;

import com.GoldenMine.parser.Variable;

public interface KoreanObject extends Comparable<KoreanObject> {
    Object getValue(String key);
    void setValue(String key, Object value);
    void setValue(int index, Object value);
    /*
    DB 고전
    정보보호 확통
    웹플 공수 심화영어
    PBE 앱제작
     */
}
