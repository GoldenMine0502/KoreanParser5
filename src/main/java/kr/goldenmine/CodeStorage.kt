package kr.goldenmine

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.CodeProcessor
import kr.goldenmine.parser.parser.*
import java.io.File
import kotlin.streams.toList

class CodeStorage {
    val codeMap = HashMap<String, MutableList<CodeProcessor>>()

    fun loadCodes() {
        val file = File("plugins/KoreanParser/codes")

        /*
        파일 이름 : 자유
        파일 첫줄 : 이벤트이름
        그 이후: 코드
         */

        forEachFilesWithDepth(file) {
            val reader = it.bufferedReader()
            val type = reader.readLine()
            val codeList = reader.lines().toList()

            val code = Code(codeList)
            val codeProcessor = CodeProcessor(code)
            codeProcessor.interpret(OriginalBackupParser(GenitiveParser(//new SentenceMultiDataParser(
                    PronounParser(VariableConnectorParser(SentenceLastParser(
                            SentencePastParser(//new BoeoParser(
                                    DefaultParser(null))))))), null)
            codeProcessor.interpret(SentenceMultiDataParser(null), null)
            codeMap.computeIfAbsent(type) { ArrayList() }.add(codeProcessor)
        }
    }

    fun forEachFilesWithDepth(folder: File, lambda: (File) -> Unit) {
        val listFiles = folder.listFiles()
        if(listFiles != null) {
            for(file in listFiles) {
                if(file.isDirectory) {
                    forEachFilesWithDepth(file, lambda)
                } else {
                    lambda.invoke(file)
                }
            }
        }
    }
}