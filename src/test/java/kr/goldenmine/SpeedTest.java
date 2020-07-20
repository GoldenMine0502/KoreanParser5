package kr.goldenmine;

import kr.goldenmine.parser.Code;
import kr.goldenmine.parser.CodeProcessor;
import kr.goldenmine.parser.parser.*;
import java.util.ArrayList;
import java.util.List;
import kr.goldenmine.parser.parser.disabled.BoeoParser;

public class SpeedTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for(long a = 0; a < 1000000L; a++) {
            System.out.println(a);
        }

        long finish = System.currentTimeMillis();


        long start2 = System.currentTimeMillis();
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 0으로 설정합니다");

        sources.add("[A]가 1000000와 같을 때까지 반복합니다");
        sources.add("[A]를 출력합니다");
        sources.add("[A]에 1을 더합니다");
        sources.add("반복문의 끝");

        sources.add("ended를 출력합니다");

        runCode(sources, false);
        long finish2 = System.currentTimeMillis();
        System.out.println(finish - start);
        System.out.println(finish2 - start2);
    }

    public static void runCode(List<String> sources, boolean debug) {
        Code code = new Code(sources);
        CodeProcessor codeProcessor = new CodeProcessor(code);
        codeProcessor.setDebug(debug);
        codeProcessor.interpret(new SentenceMultiDataParser(new PronounParser(new VariableConnectorParser(new SentenceLastParser(new BoeoParser(new DefaultParser(null)))))),
                null);
        codeProcessor.perform(null);
    }
}
