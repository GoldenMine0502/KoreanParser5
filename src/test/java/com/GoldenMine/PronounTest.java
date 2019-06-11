package com.GoldenMine;

import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.ScriptThread;
import com.GoldenMine.parser.parser.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class PronounTest {
    @Test
    public void test() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 0으로 설정합니다");
        sources.add("[A]에 7을 더한 것에 3을 곱합니다");
        sources.add("~~[A]~~를 출력합니다");
        sources.add("[B]를 [A]에 5를 더한 것으로 설정합니다");
        sources.add("--[B]--를 출력합니다");

        runCode(sources, true);
    }

    public void runCode(List<String> sources, boolean debug) {
        IParser parser = new PronounParser(new VariableConnectorParser(new SentenceParser(new DefaultParser(null))));
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
}
