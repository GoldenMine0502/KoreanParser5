package com.GoldenMine;

import com.GoldenMine.parser.Code;
import com.GoldenMine.parser.CodeProcessor;
import com.GoldenMine.parser.parser.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class Test1 {

    @Test
    public void test() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 1로 설정합니다");
        sources.add("[B]를 2로 설정합니다");
        //sources.add("[B]를 1로 설정합니다");
        sources.add("[A]를 출력합니다");
        sources.add("[B]를 출력합니다");
        sources.add("~~[B]~~를 출력합니다");
        sources.add("[A],[B]를 출력합니다");

        runCode(sources);
    }

    @Test
    public void test2() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 1로 설정합니다");
        sources.add("[B]를 1로 설정합니다");
        sources.add("[C]를 2로 설정합니다");
        sources.add("[A]와 [B]가 같다면");
        sources.add("같네를 출력합니다");
        sources.add("그렇지 않으면");
        sources.add("다르네를 출력합니다");
        sources.add("조건문의 끝");

        sources.add("[A]와 [C]가 같다면");
        sources.add("같네2를 출력합니다");
        sources.add("그렇지 않으면");
        sources.add("다르네2를 출력합니다");
        sources.add("조건문의 끝");
        runCode(sources);
    }




    public void runCode(List<String> sources) {
        Code code = new Code(sources);
        CodeProcessor codeProcessor = new CodeProcessor(code);
        codeProcessor.setDebug(true);
        codeProcessor.compile(new SentenceMultiDataParser(new PronounParser(new VariableConnectorParser(new SentenceParser(new BoeoParser(new DefaultParser(null)))))));
        codeProcessor.perform();
    }
}
