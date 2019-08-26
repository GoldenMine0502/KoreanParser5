package kr.goldenmine;

import java.util.Arrays;
import junit.framework.TestCase;
import kr.goldenmine.parser.Code;
import kr.goldenmine.parser.CodeProcessor;
import kr.goldenmine.parser.parser.*;
import java.util.ArrayList;
import java.util.List;
import kr.goldenmine.parser.parser.disabled.BoeoParser;
import org.testng.annotations.Test;

public class Test1 extends TestCase {

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

        runCode(sources, false);
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
        runCode(sources, true);
    }

    @Test
    public void test3() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 0으로 설정합니다");

        sources.add("[A]가 99와 같을 때까지 반복합니다");
        sources.add("[A]를 출력합니다");
        sources.add("[A]를 [A]에 1을 더한 것으로 설정합니다");
        sources.add("반복문의 끝");

        sources.add("ended를 출력합니다");

        /*

        int A = 0

        while(A != 5) {
          printf(A)
          A++;
        }
         */

        runCode(sources, true);
    }

    @Test
    public void test4() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 0으로 설정합니다");
        sources.add("[B]를 2으로 설정합니다");
        sources.add("[A] [B] 더합니다");
        sources.add("[A] 1 뺍니다");
        sources.add("[B] [A] 더합니다");
        sources.add("[B] [A] 뺍니다");
//        sources.add("[A]에서 4를 뺍니다");
//        sources.add("[A] 4 곱합니다");
//        sources.add("[A] 6 나눕니다");
        sources.add("[A] 출력합니다");
        sources.add("[B] 출력합니다");

        //sources.add("[A] [B] 뺀 것을 출력합니다");

        runCode(sources, true);
    }

    @Test
    public void test5() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 0으로 설정합니다");
        sources.add("[B]를 2으로 설정합니다");

        sources.add("[A] [B] 뺀 것을 출력합니다");

        runCode(sources, false);
    }

    @Test
    public void test6() {
        List<String> sources = new ArrayList<>();
        sources.add("[A]를 3 2 뺀 것으로 설정합니다");
        sources.add("[A]를 출력합니다");
        sources.add("[B]를 3으로 설정합니다");

        sources.add("[A] [B] 뺀 것을 출력합니다");

        runCode(sources, false);
    }

    @Test
    public void test7() {
        List<String> sources = new ArrayList<>();
        //sources.add("[temp]를 1로 설정합니다");
        //sources.add("[temp]를 10로 설정합니다");
        //sources.add("[constant]를 3로 설정합니다");
        //sources.add("[temp]를 5에 [constant]을 더한 값으로 설정합니다");
        sources.add("[temp]를 6을 4로 나눈 나머지에 10을 더한 것에서 5 뺀 걸로 설정합니다");
        sources.add("[temp]를 출력합니다");
        //sources.add("[A] [temp] [constant]를 출력합니다");


        runCode(sources, false);
    }

    @Test
    public void test8() {
        List<String> sources = new ArrayList<>();
        //sources.add("[temp]를 1로 설정합니다");
        sources.add("[temp2]를 3에 2 더한 것으로 설정합니다");
        sources.add("[temp2]를 출력합니다");

        runCode(sources, true);
    }

    @Test
    public void test9() {
        List<String> sources = new ArrayList<>();
        //sources.add("[temp]를 1로 설정합니다");
        sources.add("[A]를 1로 설정합니다");
        sources.add("[B]를 2로 설정합니다");

        sources.add("[A]와 [B]가 같다면");
        sources.add("[A]1를 출력합니다");
        sources.add("그렇지 않으면");
        sources.add("[B]2를 출력합니다");
        sources.add("조건문의 끝");

        runCode(sources, true);
    }

    @Test
    public void test10() {
        List<String> sources = new ArrayList<>();
        //sources.add("[temp]를 1로 설정합니다");
        sources.add("\"Hello, World!\"를 출력합니다");
        sources.add("3초 멈춥니다");
        sources.add("\"Hello, World! 3sec!\"를 출력합니다");
        sources.add("1.5초 동안 멈춥니다");
        sources.add("\"Hello, World! 1.5sec!\"를 출력합니다");
        sources.add("1500ms를 멈춥니다");
        sources.add("\"Hello, World! 1500ms!\"를 출력합니다");

        runCode(sources, false);
    }

    @Test
    public void test11() {
        List<String> sources = new ArrayList<>();
        //sources.add("[temp]를 1로 설정합니다");
        sources.add("1이 2와 같다면");
        sources.add("A를 출력합니다");
        sources.add("그렇지 않으면");
        sources.add("B를 출력합니다");
        sources.add("조건문의 끝");

        runCode(sources, true);
    }

    @Test
    public void test12() {
        List<String> sources = new ArrayList<>();
        for(int i = 0; i < 10000; i++)
        sources.add("[A]를 0으로 설정합니다");

        runCode(sources, false, false);
    }

    @Test
    public void test13() {
        List<String> sources = new ArrayList<>();

        sources.add("[A]를 3으로 설정합니다");
        sources.add("[A]가 3보다 크거나 [A]가 3과 같다면");
        sources.add("\"3보다 크거나 같음\"을 출력합니다");
        sources.add("그렇지 않으면");
        sources.add("\"3보다 작음\"을 출력합니다");
        sources.add("조건문의 끝");

        runCode(sources, true);
    }


    @Test
    public void test14() {
        List<String> sources = new ArrayList<>();

        sources.add("[A]를 3으로 설정합니다");
        sources.add("[A]가 3과 같거나 [A]가 3보다 크다면");
        sources.add("\"3보다 크거나 같음\"을 출력합니다");
        sources.add("그렇지 않으면");
        sources.add("\"3보다 작음\"을 출력합니다");
        sources.add("조건문의 끝");

        runCode(sources, true);
    }

    @Test
    public void test15() {
        List<String> sources = new ArrayList<>();

        sources.add("[A]를 3으로 설정합니다");
        sources.add("[A]가 3과 같고 [A]가 3보다 크다면");
        sources.add("\"ㅈ댐\"을 출력합니다");
        sources.add("그렇지 않으면");
        sources.add("\"정상\"을 출력합니다");
        sources.add("조건문의 끝");

        runCode(sources, true);
    }


    @Test
    public void test16() {
        List<String> sources = new ArrayList<>();

        sources.add("[A]를 2으로 초기화합니다");
        sources.add("[A]가 3보다 크거나 같다면");
        sources.add("\"3보다 크거나 같음\"을 출력합니다");
        sources.add("그렇지 않으면");
        sources.add("\"3보다 안큼\"을 출력합니다");
        sources.add("조건문의 끝");

        runCode(sources, false);
    }

    @Test
    public void test17() {
        List<String> sources = new ArrayList<>();


        sources.add("[A]를 0으로 초기화합니다");
        sources.add("[A]가 10보다 작거나 같으면 반복합니다");
        sources.add("[A]가 1에 2를 더한 것보다 크거나 같다면");
        sources.add("\"3보다 크다 [A]\"을 출력합니다");
        sources.add("그렇지 않으면");
        sources.add("음을 출력합니다");
        sources.add("조건문의 끝");
        sources.add("[A]를 [A]에 1을 더한 것으로 설정합니다");
        sources.add("반복문의 끝");

        runCode(sources, false);
    }

    @Test
    public void test18() {
        List<String> sources = new ArrayList<>();

        sources.add("[A]를 1으로 초기화합니다");
        sources.add("[A]를 [A]에 1 더한 것으로 설정합니다");
        sources.add("[A]를 출력합니다");

        runCode(sources, false);
    }

    @Test
    public void test19() {
        List<String> sources = new ArrayList<>();

        sources.add("[A]를 1237776699898999의 각 자리수의 합계로 초기화합니다");
        sources.add("[A]를 출력합니다");
        sources.add("[B]를 123123의 자리수로 초기화합니다");
        sources.add("\"123123은 [B]자리수 입니다\"를 출력합니다");

        runCode(sources, false);
    }

    @Test
    public void test20() {
        List<String> sources = new ArrayList<>();

        sources.add("[A]를 1과 2로 xy좌표를 만든 것으로 초기화합니다");
        sources.add("[A]를 출력합니다");
        sources.add("[A]의 x좌표를 3으로 설정합니다");
        sources.add("[A]를 출력합니다");

        runCode(sources, false);
    }

    @Test
    public void test20_2() {
        List<String> sources = Arrays.asList(
                "[A]를 1과 2로 xy좌표를 만든 것으로 초기화합니다",
                "[A]를 출력합니다",
                "[A]의 x좌표를 3으로 설정합니다",
                "[A]를 출력합니다"
        );

        runCode(sources, false);
    }


    @Test
    public void test21() {
        List<String> sources = Arrays.asList(
                "1과 1이 같다는 것를 출력합니다",
                "1과 1 같다는 것를 출력합니다",
                "1 1 같다는 것를 출력합니다"
        );

        runCode(sources, false);
    }

    /*
        IF문 또는 WHILE문이 안길 수 있는가?
        예시) A가 B와 같다면을 출력합니다
        문법 자체로도 문제고 논리적으로 어색하기 때문에 컴파일 자체가 불가능하도록 해야 함
        IF문은 어찌어찌 조사를 이용해 고비를 넘겼으나, 반복문은 어려울 것으로 예상됨.

        Predicate마다 필요한 조각조각을 인터페이스화시켜 이를 구분할 방법이 없을까
     */

    public void runCode(List<String> sources, boolean debug) {
        runCode(sources, debug, true);
    }

    public void runCode(List<String> sources, boolean debug, boolean perform) {
        Code code = new Code(sources);
        CodeProcessor codeProcessor = new CodeProcessor(code);
        codeProcessor.setDebug(debug);
        codeProcessor.compile(
                new OriginalBackupParser(new GenitiveParser(new SentenceMultiDataParser(
                new PronounParser(new VariableConnectorParser(new SentenceLastParser(
                        new SentencePastParser(new PredicateParser(new BoeoParser(
                                new DefaultParser(null)))))))))),
                null);
        if(perform)
            codeProcessor.perform(null);
    }
}
