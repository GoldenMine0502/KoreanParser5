package com.GoldenMine.parser.parser;

import com.GoldenMine.parser.ParseContext;

public class VariableValueParser implements IParser {
    @Override
    public void parse(ParseContext context) {
        context.getSentences().stream()
                .flatMap(it->it.getMap().values().stream())
                .flatMap(it->it.getVariables().stream())
                .forEach(it->it.set(it.get()));
    }

    //[A]의 [B]를 출력합니다
    //화면의 길이를 출력합니다
}
