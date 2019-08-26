package kr.goldenmine.parser

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.objects.Variable
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.defaults.ObjectString
import kr.goldenmine.parser.parser.IParser
import kr.goldenmine.parser.predicatespecific.IPredicateSpecific
import kr.goldenmine.parser.predicatespecific.IPredicateSpecificPost
import java.util.*

class CodeProcessor(
        //    private static final HashSet<String> exceptCodes = new HashSet<>();
        //
        //    static {
        //        for (IPredicateSpecific specific : PredicateStorage.INSTANCE.getSpecifics()) {
        //            exceptCodes.addAll(specific.getOthers());
        //        }
        //    }

        private val code: Code) {
    private var debug: Boolean = false

    fun setDebug(debug: Boolean) {
        this.debug = debug
    }

    /*
    만약 A가 B보다 크거나 같다면

     */

    fun compile(parser: IParser, metadata: List<Any>?) {
        if (debug) {
            println("compiling... copyright by goldenmine (김태원) blog.naver.com/ehe123")
        }

        val sourceCode = code.sourceCode
        for (line in sourceCode.indices) {
            parser.parse(code, sourceCode[line], line, debug, metadata)
            if (debug) {
                println("compiled line " + line + ": " + sourceCode[line].parsedMap + ", " + sourceCode[line].isNoParse)
                sourceCode[line].sentences.forEach {
                    println(" (sentence) noparse: ${sourceCode[line].isNoParse} has multiprocess: ${it.multiProcessData != null} map: ${it.map} predicate: ${it.서술어.defaultSentence}")
                }

                println("\n\n\n\n\n")

//                sourceCode[line].sentences.forEach {
//                    println("${it.서술어.defaultSentence}, ${it.map}")
//                }
            }
        }

        if (debug) {
            println("compiled. ready to perform")
        }
    }


    /*
    ex ) [A]가 [B]와 같을 때 반복합니다
    ex2) [A]가 [B]와 같을 때까지 반복합니다



     */

    fun perform(metadata: List<Any>?) {
        /*
        접속사 개발

         */

        val sourceCode = code.sourceCode
        val LOCAL = VariableStorage.createVariableStorage("temp")

        var performIndex = 0

        var postedSpecific: IPredicateSpecific? = null

        while (performIndex < sourceCode.size) {
            try {
                val parseContext = sourceCode[performIndex]

                if (parseContext.isNoParse) {
                    val sentence = parseContext.sentences[0]
                    //println("noparse $performIndex ${sentence.specific != null}")
                    if (sentence.specific != null) {
                        performIndex = sentence.specific.execute(performIndex, metadata, sentence, sentence.multiProcessData, null)
                        //println("${sentence.multiProcessData}")
                    }
                } else {
                    val sentences = parseContext.sentences
                    val pronounInfoList = parseContext.pronounInfoList
                    val variableReturns = ArrayList<Variable?>()

                    var pronounInfoIndex = 0

                    for (sentenceIndex in sentences.indices) {
                        val sentence = sentences[sentenceIndex]
                        applyVariable(sentence, LOCAL)

                        //val result = sentence.서술어.perform(sentence, LOCAL)
                        var pronoun: PronounInfo? = null
                        //System.out.println(performIndex + ": " + sentence.get서술어().getDefaultSentence() + ": " + ((result != null) ? result.get() : "null"));
                        //println(performIndex.toString() + ": " + sentence.서술어.defaultSentence + ": " + if (result != null) result.get() else "null")
                        if (pronounInfoIndex < pronounInfoList.size) {
                            val info = pronounInfoList[pronounInfoIndex]
                            if (sentence === info.modifying) {
                                // TO/DO PronounParser에 이미 존재하는 대명사는 제외 코드 작성
                                if (debug) println("codeprocessor-pronoun: $pronounInfoIndex ${info.modifying.서술어.defaultSentence}")

                                pronoun = info
                                pronounInfoIndex++
                            }
                        }

                        val result: Variable?

                        if (pronoun != null) {
                            result = pronoun.modifiedKeyPronoun.perform(sentence, metadata, LOCAL)
                            pronoun.context.variables!![pronoun.variableIndex] = result
                        } else {
                            result = sentence.서술어.perform(sentence, metadata, LOCAL)
                        }


                        variableReturns.add(result)
                        if (debug)
                            println("codeprocessor-result: $performIndex $result // $variableReturns")

                        //println("$performIndex: ${sentence.specific!=null}")

                        if (postedSpecific != null) {
                            postedSpecific.perform(sentence, metadata, LOCAL, variableReturns)
                            postedSpecific.execute(performIndex, metadata, sentence, sentence.multiProcessData, variableReturns)
                            postedSpecific = null
                        }

                        if (sentence.specific != null) {
                            if (sentence.specific is IPredicateSpecificPost) {
                                postedSpecific = sentence.specific
                            } else {
                                sentence.specific.perform(sentence, metadata, LOCAL, variableReturns)
                                performIndex = sentence.specific.execute(performIndex, metadata, sentence, sentence.multiProcessData, variableReturns)
                            }
                        }
                    }


                    // 바뀐 값들 원래대로 되돌려놓음
                    sentences.forEach {
                        it.map.forEach { (t, u) ->
                            val originalList = it.originalMap[t]!!.variables!!
                            val newList = ArrayList<Variable?>(originalList.size)
                            originalList.forEach { newList.add(Variable(it!!)) }

                            it.map[t]!!.variables = newList
                        }
                    }


                    //                    Sentence lastSentence = sentences.get(sentences.size() - 1);
                    //
                    //                    for (int pronounInfoIndex = 0; pronounInfoIndex < pronounInfoList.size(); pronounInfoIndex++) {
                    //                        PronounInfo info = pronounInfoList.get(pronounInfoIndex);
                    //                        applyVariable(info.getModifying(), LOCAL);
                    //
                    //                        Sentence modified = info.getModified();
                    //                        Variable result = info.getModifiedKeyPronoun().perform(info.getModifying(), LOCAL);
                    //                        variableReturns.add(result);
                    //
                    //                        info.getContext().getVariables().set(info.getVariableIndex(), result);
                    //
                    //                        if (modified.getSpecific() != null) {
                    //                            performIndex = modified.getSpecific().execute(performIndex, modified, modified.getMultiProcessData(), variableReturns);
                    //                        }
                    //                    }
                    //
                    //                    applyVariable(lastSentence, LOCAL);
                    //                    Variable result = lastSentence.get서술어().perform(lastSentence, LOCAL);
                    //                    variableReturns.add(result);
                    //
                    //                    // sentence 순차순으로 가되, Pronoun으로 판단된 문장은 명사에 리턴값을 넣는 코드를 넣어야 함
                    //                    if (lastSentence.getSpecific() != null) {
                    //                        performIndex = lastSentence.getSpecific().execute(performIndex, lastSentence, lastSentence.getMultiProcessData(), variableReturns);
                    //                    }

                }
            } catch (ex: Exception) {
                println("line " + performIndex + "에서 에러발생")
                ex.printStackTrace()
                break
            }

            performIndex++
            //sleep(500)
            //            try {
            //                Thread.sleep(500L);
            //            } catch (InterruptedException e) {
            //                e.printStackTrace();
            //            }
        }
    }

    fun applyVariable(sentence: Sentence, storage: VariableStorage) {
        val map = sentence.map

        val replaceable = sentence.changeVar

        for (key in map.keys) {
            val context = map[key]
            if (context != null) {
                val variables = context.variables!!
                val variableGenitives = context.genitiveList!!

                for (variableIndex in variables.indices) {
                    val variable = variables[variableIndex]

                    if (variable != null && replaceable[key]!!) {
                        val genitives = variableGenitives[variableIndex]

                        if (genitives != null) {
                            var currentIndex = 0

                            var result: KoreanObject = genitives[0].get()

                            if(result is ObjectString && VariableStorage.isVariable(result.getRoot() as String)) {
                                val value = result.getRoot() as String
                                val valueCut = value.substring(1, value.length - 1)
                                if (storage.hasVariable(valueCut)) {
                                    result = storage.getVariable(valueCut)!!.get()
                                } else if (VariableStorage.GLOBAL.hasVariable(valueCut)) {
                                    result = VariableStorage.GLOBAL.getVariable(valueCut)!!.get()
                                } else {
                                    val variable = Variable(0, false)
                                    result = variable.get()
                                    storage.setVariable(valueCut, variable)
                                }
                            }

                            if(debug)
                                println("result: $result")

                            while (currentIndex < genitives.size - 1) {
                                val next = genitives[currentIndex + 1]

                                val tempResult = result.getValue(next.get().toString())
                                if(tempResult != null) {
                                    result = tempResult
                                } else {
                                    throw RuntimeException("속성 ${next.get()}이(가) 존재하지 않습니다.")
                                }
                                currentIndex++
                            }

                            if (debug) {
                                println("genitiveResult: $result")
                            }

                            variable.set(result)
                        } else if (variable.mode == VariableMode.STRING_MODE) {
                            val value = variable.stringValue()

                            if (VariableStorage.isVariable(value)) {
                                //println(value)
                                val sb = StringBuilder(value.substring(1, value.length - 1))
                                processVariable(sb, 0, false, storage)
                                val valueCut = sb.toString()
                                //println(valueCut)

                                val variableTemp: Variable?

                                if (storage.hasVariable(valueCut)) {
                                    variableTemp = storage.getVariable(valueCut)
                                } else if (VariableStorage.GLOBAL.hasVariable(valueCut)) {
                                    variableTemp = VariableStorage.GLOBAL.getVariable(valueCut)
                                } else {
                                    variableTemp = Variable(0, false)
                                    storage.setVariable(valueCut, variableTemp)
                                }

                                if (variableTemp != null) {
                                    variables[variableIndex] = variableTemp
                                } else {
                                    throw ConcurrentModificationException(valueCut + "에 대한 변수 값이 초기화되지 않았습니다. (thread safety 문제) ")
                                }
                            } else {
                                val sb = StringBuilder(value)
                                processVariable(sb, 0, false, storage)
                                //variable.castCompelNoMaintain(Variable.VariableMode.STRING_MODE)
                                variable.set(sb.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun processVariable(sb: StringBuilder, pos: Int, inner: Boolean, storage: VariableStorage): Int {
            var pos = pos
            val start = pos

            while (pos < sb.length) {
                val ch = sb[pos]
                pos++
                if (ch == '[')
                    pos = processVariable(sb, pos, true, storage)
                else if (ch == ']')
                    break
            }

            if (inner) {
                try {
                    val key = sb.substring(start, pos - 1)
                    val `val` = storage.getVariable(key)

                    if (`val` != null) {
                        sb.delete(start - 1, pos)

                        val valToStr = `val`.get().toString()
                        sb.insert(start - 1, valToStr)
                        pos = start - 1 + valToStr.length
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }

            return pos
        }
    }
}
