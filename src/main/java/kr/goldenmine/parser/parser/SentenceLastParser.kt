package kr.goldenmine.parser.parser

import kr.goldenmine.impl.CompileException
import kr.goldenmine.parser.Code
import kr.goldenmine.parser.Context
import kr.goldenmine.parser.ParseContext
import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.predicate.IPredicate
import kr.goldenmine.parser.predicate.PredicateStorage
import kr.goldenmine.parser.predicatespecific.IPredicateSpecific
import java.util.*
import java.util.stream.Collectors

class SentenceLastParser(private val parser: IParser?) : IParser {
    //https://blog.woniper.net/239 창 여러개 띄우기
    override fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?) {
        parser?.parse(code, parseContext, index, debug, metadata)
        if (debug)
            println("executing SentenceLastParser")
        if (!parseContext.isNoParse) {
            val sentences = parseContext.sentences

            val predicateContexts = parseContext.parsedMap["서술어"]
            //val toAddMap = HashMap<String, MutableList<Context>>()

            val toAddMap = HashMap<String, MutableList<Context>>()

            val map = HashMap(parseContext.parsedMap)
            map.entries.forEach { it.setValue(ArrayList(it.value)) } // copy list

            if (predicateContexts != null) {
                for (predicatesIndex in predicateContexts.indices) {
                    val context = predicateContexts[predicatesIndex]

                    if (debug)
                        println("executing SentenceLastParser-splitPredicateSource $map")
                    val predicatesSplited = splitPredicateSource2(context)
                    val predicatesSplitedSeo = predicatesSplited["서술어"]!![0]
                    val predicatesSplitedTemp = predicatesSplited["temp"]!!
                    context.source = predicatesSplitedSeo.source
                    context.posStart = predicatesSplitedSeo.posStart
                    context.posFinish = predicatesSplitedSeo.posFinish


                    if(predicatesSplited.containsKey("보어")) {
                        parseContext.parsedMap.computeIfAbsent("보어") { ArrayList() }.addAll(predicatesSplited["보어"]!!)
                        map.computeIfAbsent("보어") { ArrayList() }.addAll(predicatesSplited["보어"]!!)
                    }

                    if (debug)
                        println("SentenceLastParser-splited: $predicatesSplited")
                    val predicates = PredicateStorage.INSTANCE.getPredicate(context.source)

                    var specific: IPredicateSpecific? = null
                    val predicate: Pair<IPredicate, Pair<HashMap<String, Context>, HashMap<String, MutableList<Context>>>?>

                    if (debug)
                        println("executing SentenceLastParser-getPredicateSubs")
                    if (predicates != null) {
                        try {
                            predicate = predicates.stream().map {
                                val copied = ArrayList(predicatesSplitedTemp)
                                if (it.neededSentenceElements.size + it.optionalSentenceElements.size == 1) {
                                    val result = concatContexts(predicatesSplitedTemp)
                                    if (result != null) {
                                        copied.clear()
                                        copied.add(result)
                                    }

                                    if (debug)
                                        println("sentencelastparser-splited3: $result")
                                }

                                val map2 = HashMap(map)
                                map2.entries.forEach { it.setValue(ArrayList(it.value)) } // copy list

                                val pair = Pair(it, getPredicateSubs(parseContext, map2, context, it, copied, debug, metadata))
                                if (pair.second != null) {
                                    map.clear()
                                    map.putAll(map2)
                                }
                                pair
                            }.filter { it!!.second != null }.findFirst().get()
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            throw CompileException("서술어 [ " + context.source + " ]에 대한 문장성분이 제대로 갖추어지지 못했습니다.")
                        }
                    } else {
                        throw CompileException("[ " + context.source + " ]에 대한 서술어 정보가 없습니다.")
                    }
                    // specific 파싱
                    for (predicateSpecific in PredicateStorage.INSTANCE.specifics) {
                        val verified = predicateSpecific.verify(context.source, ParseContext.komoran.analyze(context.source))
                        if (verified) {
                            specific = predicateSpecific
                            break
                        }
                    }


                    //println("A: $index ${parseContext.parsedMap}")

                    // 순서에 맞춰 기존의 ParsedMap에 새로 만들어진 Context들을 넣어야 함

                    predicate.second?.second?.forEach { t, u ->
                        toAddMap.computeIfAbsent(t) { ArrayList() }.addAll(u)
                        //println("sentencelastparser: $t $u")
                    }


                    if (predicate.second != null) {
                        val sentence = Sentence(predicate.second!!.first, predicate.first, specific)
                        sentences.add(sentence)
                    } else {
                        throw CompileException("서술어 [" + context.source + "]에 대한 문장성분이 제대로 갖추어지지 못했습니다.2")
                    }
                }

                toAddMap.forEach { (t, u) ->
                    parseContext.parsedMap.computeIfAbsent(t) { ArrayList() }.addAll(u)
                }
            } else {
                throw CompileException("해당 문장에 서술어가 존재하지 않습니다.")
            }
        }
    }
}


