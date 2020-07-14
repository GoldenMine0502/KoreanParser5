package kr.goldenmine.parser.parser

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.ParseContext
import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.predicatespecific.IPredicateSpecific

class SentenceMultiDataParser(private val parser: IParser?) : IParser {

    override fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?) {
        parser?.parse(code, parseContext, index, debug, metadata)
        if (!parseContext.isNoParse) {
            val sentences = parseContext.sentences

            //for (int sentenceIndex = ; sentenceIndex < sentences.size(); sentenceIndex++)
            run {
                val sentence = sentences[sentences.size - 1]
                val specific = sentence.specific

                if (specific != null) {
                    val others = specific.others
                    //System.out.println("not null specific " + index + ", " + others.size());

                    var overlap = 0
                    //val multiIndex = arrayOfNulls<Int>(others.size + 1)
                    val multiIndex = IntArray(others.size + 1)
                    multiIndex[0] = index
                    for (i in 1 until multiIndex.size) {
                        multiIndex[i] = -1
                    }
                    var set = 0

                    for (line in index + 1 until code.sourceCode.size) {
                        val parseContext = code.sourceCode[line]
                        val innerSentences = parseContext.sentences
                        val source = parseContext.source

                        for (innerSentenceIndex in innerSentences.indices) {
                            val innerSentence = innerSentences[innerSentenceIndex]

                            if (specific === innerSentence.specific) {
                                overlap++
                            }
                        }
                        if (parseContext.isNoParse) {
                            val containsSource = others.indexOf(source)
                            //System.out.println(source + ", " + others + ", " + containsSource);
                            if (containsSource >= 0) {
                                if (overlap == 0) {
                                    multiIndex[containsSource + 1] = line
                                    set++
                                    if (set >= others.size) {
                                        break
                                    }
                                } else {
                                    if (containsSource == others.size - 1) {
                                        overlap--
                                    }
                                }
                            }
                        }

                        //System.out.println(line + ", " + overlap);
                    }

                    //println("${multiIndex[0]} ${multiIndex[1]} ")

                    val data = IPredicateSpecific.MultiProcessData(multiIndex.toCollection(ArrayList()))

                    sentence.multiProcessData = data
                    for (i in 1 until multiIndex.size) {
                        //System.out.println(multiIndex[i]==null);
                        val value = multiIndex[i]
                        if (value != -1) {
                            val secondary = code.sourceCode[value]
                            //                            List<Sentence> secSentences = secondary.getSentences();
                            //
                            //                            for(int senIndex = 0; senIndex < secSentences.size(); senIndex++) {
                            //                                Sentence secSentence = secSentences.get(senIndex);
                            //
                            //                            }
                            if (secondary.isNoParse) {
                                val defaultSentence = Sentence(HashMap(), sentence.서술어, sentence.specific)
                                defaultSentence.multiProcessData = data
                                secondary.sentences.add(defaultSentence)
                            }
                        }
                    }
                }
            }
        }
    }
}
