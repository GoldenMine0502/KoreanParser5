package com.GoldenMine;

import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.VariableStorage;
import com.GoldenMine.parser.parser.DefaultParser;
import com.GoldenMine.parser.parser.IParser;
import com.GoldenMine.parser.parser.SentenceParser;
import com.GoldenMine.parser.parser.VariableConnectorParser;
import com.GoldenMine.parser.postposition.JosaStorage;
import org.junit.Before;
import org.junit.Test;

public class DefaultParserTest {


    @Before
    public void init() {

    }

    public void parseAndPrint(ParseContext context, IParser parser) {
        context.setParser(parser);
        context.parse();
        System.out.println("================================");
        System.out.println(context.getSource());
        System.out.println(context.getParsedMap());
        System.out.println(context.getSentences());
    }

    public void perform(ParseContext context) {
        context.getSentences().forEach(t -> t.get서술어().perform(t, VariableStorage.createVariableStorage()));
    }

    @Test
    public void performance() {
        final int N = 10000;

        // initialize
        JosaStorage.INSTANCE.getAllJosaList();

        DefaultParser parser = new DefaultParser(null);

        System.out.println("start");
        long start = System.currentTimeMillis();

        for(int i = 0; i < N; i++) {
            ParseContext context = new ParseContext("음식을 만들었다");
            context.setParser(parser);
            context.parse();
        }

        System.out.println((System.currentTimeMillis() - start) + "ms");
    }

    @Test
    public void test() {
        parseAndPrint(new ParseContext("음식을 만들었다"), new DefaultParser(null));
    }


    @Test
    public void test2() {
        parseAndPrint(new ParseContext("맛있는 음식을 만들었다"), new DefaultParser(null));
    }

    @Test
    public void test3() {
        parseAndPrint(new ParseContext("맛있는 \"음식\"을 만들었다"), new DefaultParser(null));
    }

    @Test
    public void test4() {
        parseAndPrint(new ParseContext("맛있는 \"음\'식\"을 만들었다"), new DefaultParser(null));
    }

    @Test
    public void test5() {
        parseAndPrint(new ParseContext("어머니께서 맛있는 음식을 만들었다"), new DefaultParser(null));
    }


    @Test
    public void test6() {
        parseAndPrint(new ParseContext("그가 맛있는 음식을 만들었다"), new DefaultParser(null));
    }

    @Test
    public void test7() {
        parseAndPrint(new ParseContext("그가 맛있는 음식을 친구와 만들었다"), new DefaultParser(null));
    }

    @Test
    public void test8() {
        ParseContext context = new ParseContext("출력한다 A를");

        parseAndPrint(context, new SentenceParser(new DefaultParser(null)));
        perform(context);
    }

    @Test
    public void test9() {
        ParseContext context = new ParseContext("출력한다 A와 B를");

        parseAndPrint(context, new VariableConnectorParser(new SentenceParser(new DefaultParser(null))));
        perform(context);
    }
}
