package com.GoldenMine.test;

import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.parser.DefaultParser;

public class TestDefaultParser {
    public static void main(String[] args) {
        ParseContext context;
        context = new ParseContext("만들었다 음식을");

        context.setParser(new DefaultParser(null));
        context.parse();
        System.out.println(context.getParsedMap());
    }
}

//GRADLE_OPTS=-Dfile.encoding=UTF-8
