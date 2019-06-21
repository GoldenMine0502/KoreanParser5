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

    @Test
    public void test3() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 0으로 설정합니다");
        sources.add("[A]가 5와 같으면 반복합니다");
        sources.add("[A]를 출력합니다");
        sources.add("반복문의 끝");
        sources.add("ended를 출력합니다");

        runCode(sources);
    }

    /*
    Q) IF문 또는 WHILE문이 안길 수 있는가?
        예시) 만약 A가 B와 같다면을 출력합니다
        문법 자체로도 문제고 논리적으로 어색하기 때문에 컴파일 자체가 불가능하도록 해야 함
        IF문은 어찌어찌 조사를 이용해 고비를 넘겼으나, 반복문은 어려울 것으로 예상됨.

        Predicate마다 필요한 조각조각을 인터페이스화시켜 이를 구분할 방법이 없을까
     */


    public void runCode(List<String> sources) {
        Code code = new Code(sources);
        CodeProcessor codeProcessor = new CodeProcessor(code);
        codeProcessor.setDebug(true);
        codeProcessor.compile(new SentenceMultiDataParser(new PronounParser(new VariableConnectorParser(new SentenceParser(new BoeoParser(new DefaultParser(null)))))));
        codeProcessor.perform();
    }
}
