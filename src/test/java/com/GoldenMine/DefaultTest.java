package com.GoldenMine;

import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.ScriptThread;
import com.GoldenMine.parser.parser.DefaultParser;
import com.GoldenMine.parser.parser.IParser;
import com.GoldenMine.parser.parser.SentenceParser;
import com.GoldenMine.parser.parser.VariableConnectorParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class DefaultTest {

    @Test
    public void test() {
        IParser parser =  new VariableConnectorParser(new SentenceParser(new DefaultParser(null)));

        List<ParseContext> contexts = new ArrayList<>();

        ParseContext code1 = new ParseContext("[A]를 BASDF로 설정합니다");
        code1.setParser(parser);
        code1.parse();
        contexts.add(code1);

        ParseContext code2 = new ParseContext("[A]를 출력합니다");
        code2.setParser(parser);
        code2.parse();
        contexts.add(code2);

        ScriptThread thread = new ScriptThread(contexts);
        thread.performAll();
    }

    @Test
    public void test2() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 \"철수가 학교에 간다\"로 설정합니다");
        sources.add("[A]에 1231214292992723333292727212721721241을 더합니다");
        sources.add("[A]를 출력합니다");

        runCode(sources, false);
    }

    @Test
    public void test3() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 0으로 설정합니다");
        sources.add("[A]에 100을 더합니다");
        sources.add("[A]에 3을 곱합니다");
        sources.add("[A]를 5로 나눕니다");
        sources.add("[A]ㅎㅎlllk를 출력합니다");

        runCode(sources, false);
    }


    @Test
    public void test4() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 0으로 설정합니다");
        sources.add("[A]에 100을 더한 것에 3을 곱합니다");
        sources.add("[A]에 3을 곱합니다");
        sources.add("[A]를 5로 나눕니다");
        sources.add("[A]ㅎㅎ를 출력합니다");

        runCode(sources, false);
    }

    @Test
    public void test5() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 0으로 설정합니다");
        sources.add("[A]에 100을 더한 것에 3을 곱합니다");

        runCode(sources, false);
    }

    @Test
    public void performance() {
        List<String> sources = new ArrayList<>();

        for(int i = 0; i < 10000; i++)
            sources.add("[A]를 0으로 설정합니다");

        runCode(sources, true);
    }

    @Test
    public void performance2() {
        List<String> sources = new ArrayList<>();

        for(int i = 0; i < 10000; i++) {
            sources.add("[A]를 0으로 설정합니다");
            sources.add("[A]에 100을 더한 것에 3을 곱합니다");
        }
        //sources.add("[A]에 100을 더한 것에 3을 곱합니다");

        runCode(sources, true);
    }


    public void runCode(List<String> sources, boolean debug) {
        IParser parser = new VariableConnectorParser(new SentenceParser(new DefaultParser(null)));
        List<ParseContext> parseContexts = new ArrayList<>();

        long start = System.currentTimeMillis();
        for(int i = 0; i < sources.size(); i++) {
            ParseContext parseContext = new ParseContext(sources.get(i));
            parseContext.setParser(parser);
            parseContext.parse();
            //System.out.println(parseContext.getParsedMap());
            parseContexts.add(parseContext);
        }

        long start2 = System.currentTimeMillis();
        ScriptThread thread = new ScriptThread(parseContexts);
        thread.performAll();

        long start3 = System.currentTimeMillis();

        if(debug) {
            System.out.println((start2 - start) + "ms on compiling");
            System.out.println((start3 - start2) + "ms on running");
        }
    }

//    @Test
//    public void performance() {
//        IParser parser =  new VariableConnectorParser(new SentenceParser(new DefaultParser(null)));
//
//        long start = System.currentTimeMillis();
//        List<ParseContext> contexts = new ArrayList<>();
////        for(int i = 0; i < 100000; i++)
//        {
//            ParseContext code1 = new ParseContext("[A]를 출력합니다");
//            code1.setParser(parser);
//            code1.parse();
//            contexts.add(code1);
//        }
//        long start2 = System.currentTimeMillis();
//
//        ScriptThread thread = new ScriptThread(contexts);
//        thread.performAll();
//
//        long start3 = System.currentTimeMillis();
//
//        System.out.println((start2 - start) + "ms, " + (start3-start2) + "ms");
//    }
}
