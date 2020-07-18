package kr.goldenmine

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.CodeProcessor
import kr.goldenmine.parser.parser.*
import java.io.File
import java.util.*

fun main() {
    val scan = Scanner(System.`in`)

    print("문서를 읽을 파일을 입력하세요: ")
    val route = scan.next()

    print("디버그 모드(true,false): ")
    val debug = scan.nextBoolean()

    runCode(File(route).readLines(), debug, true)
}

fun runCode(sources: List<String>, debug: Boolean, perform: Boolean) {
    val code = Code(sources)
    val codeProcessor = CodeProcessor(code)
    codeProcessor.setDebug(debug)
    codeProcessor.compile(
            OriginalBackupParser(
                    GenitiveParser( //new SentenceMultiDataParser(
                            PronounParser(
                                    VariableConnectorParser(
                                            SentenceLastParser(
                                                    SentencePastParser( //new BoeoParser(
                                                            DefaultParser(null)
                                                    )
                                            )
                                    )
                            )
                    )
            ) //))
            , null)
    codeProcessor.compile(SentenceMultiDataParser(null), null)
    //        try {
//            Thread.sleep(1000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    if (perform) codeProcessor.perform(null)
}