package kr.goldenmine.parser.parser

import kr.goldenmine.impl.CompileException
import kr.goldenmine.objects.Variable
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.parser.*
import kr.goldenmine.parser.pronoun.PronounStorage

class PronounParser(private val parser: IParser?) : IParser {

    override fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?) {
        parser?.parse(code, parseContext, index, debug, metadata)

        if (!parseContext.isNoParse) {

            val predicates = parseContext.parsedMap["서술어"]
            val sentences = parseContext.sentences

            if (predicates != null) {
                val alreadyAdded = HashSet<Variable?>()

                for (sentenceIndex in sentences.indices) {
                    val sentence = sentences[sentenceIndex]
                    val sentencePos = predicates[sentenceIndex].posStart

                    val map = sentence.map
                    for (key in map.keys) {
                        if (key != "서술어") {
                            val context = map[key]!!
                            if (context.variables != null) {
                                for (variableIndex in 0 until context.variables!!.size) {
                                    val variable = context.variables!![variableIndex]
                                    if (variable != null && variable.mode == VariableMode.STRING_MODE) {
                                        val modifiedKey = variable.stringValue()

                                        if (!alreadyAdded.contains(variable) && PronounStorage.isPronoun(variable.stringValue())) {
                                            alreadyAdded.add(variable)
                                            var modifying: Sentence? = null
                                            var modifyingIndex = 0

                                            for (modifyingIndexFor in sentenceIndex - 1 downTo 0) {
                                                val modifyingFor = sentences[modifyingIndexFor]
                                                val modifyingForIndex = predicates[modifyingIndexFor].posStart

                                                if (sentencePos > modifyingForIndex) {
                                                    modifying = modifyingFor
                                                    modifyingIndex = modifyingForIndex
                                                    break
                                                }
                                            }

                                            if (modifying != null && modifiedKey != null) {
                                                val pronoun = PronounStorage.getPronoun(modifiedKey)

                                                if (pronoun != null) {
                                                    val info = PronounInfo(modifying, modifyingIndex, sentence, sentenceIndex, context, variableIndex, modifiedKey!!, pronoun)
                                                    parseContext.pronounInfoList.add(info)
                                                } else {
                                                    throw CompileException("[ $modifiedKey ] 해당 명사에 대한 정보가 없습니다.")
                                                }
                                            } else {
                                                throw CompileException("대명사이지만 수식받는 서술어가 없습니다.")
                                            }
                                        }
                                    }
                                }
                            } else {
                                throw CompileException("${index}번 코드에서 변수가 발견되지 않았습니다.")
                            }
                        }
                    }
                }
            }
        }
    } // [A]에 100을 더한 것과 [B]에 3을 더한 것에 3과 4를 더합니다
}
