package com.GoldenMine.object;

import com.GoldenMine.parser.Variable;

public interface KoreanObjectCreator {
    String getType();
    Variable.VariableMode getValueType(String key);

    // [i] = long
    // [d] = double
    // [s] = string
    // [b] = boolean
    // [i], [d] = long, double을 파라미터로 받음.
    // [i] and [d] = and를 쓰면 접속조사와 같은 것으로 생각.
    String getRegex();

    KoreanObject getInstance();
}
