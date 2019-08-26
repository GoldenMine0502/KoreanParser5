package kr.goldenmine.parser.parser

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.ParseContext
import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.parseVariable
import kr.goldenmine.parser.postposition.JosaCommunity
import kr.goldenmine.parser.postposition.JosaStorage
import kr.goldenmine.parser.predicate.IPredicate
import kr.goldenmine.parser.predicate.ValueScope
import java.lang.RuntimeException
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

// TODO 모든 코드 최적화 요망

fun parsedMapToIndices(parseContext: ParseContext): MutableList<Pair<Context, String>> {
    val indices = ArrayList<Pair<Context, String>>()

    parseContext.parsedMap.forEach { (t, u) ->
        u.forEach {
            indices.add(Pair(it, t))
        }
    }

    indices.sortWith(Comparator { it, it2 -> it.first.posStart.compareTo(it2.first.posStart) })

    return indices
}

fun mapToIndices(map: HashMap<String, Context>): MutableList<Pair<Context, String>> {
    val indices = ArrayList<Pair<Context, String>>()

    map.forEach { (t, u) ->
        indices.add(Pair(u, t))
    }

    indices.sortWith(Comparator { it, it2 -> it.first.posStart.compareTo(it2.first.posStart) })

    return indices
}

//fun <T> binarySearch(value:T, list: List<T>, comparator: Comparator<T>): Int = binarySearch(value, list, comparator, list.size / 2)

fun <T> linearSearch(value: T, list: List<T>, comparator: Comparator<T>): Int {
    for(i in  0 until list.size) {
        val x = list[i]
        if(comparator.compare(x, value) == 0) {
            return i
        }
    }

    return -1
}

fun <T> binarySearch(value: T, list: List<T>, comparator: Comparator<T>): Int {
    var index = list.size / 2

    while (true) {
        val currentValue = list[index]
        val compared = comparator.compare(value, currentValue)
        val nextIndex: Int
        nextIndex = when {
            compared == 0 -> return index
            compared > 0 -> (index + list.size) / 2
            compared < 0 -> index / 2
            else -> throw RuntimeException("오류날 일이 없다 가독성을 위하여!")
        }

        if (index == nextIndex) {
            return -1
        }
        index = nextIndex
    }
}

val comparatorForFindPos = Comparator<Pair<Context, String>> { it, it2-> it.first.posStart.compareTo(it2.first.posStart) }

fun findPos(context: Pair<Context, String>, collection: List<Pair<Context, String>>): Int {
    return binarySearch(context, collection, comparatorForFindPos)
}

fun findPosLinear(context: Pair<Context, String>, collection: List<Pair<Context, String>>): Int {
    return linearSearch(context, collection, comparatorForFindPos)
}

fun findNears(elements: List<String>, elementConditions: List<ValueScope>, map: HashMap<String, MutableList<Context>>, predicateCurrentIndex: Int, subMap: HashMap<String, Context>, toAddMap: HashMap<String, MutableList<Context>>,
              predicateContextIndex: Int, predicateSplited: MutableList<Context>, list: MutableList<Pair<Context, String>>,
              selectable: Boolean, debug: Boolean): HashMap<String, Context> {
    val types = HashSet(elements)
    var currentIndex =  predicateCurrentIndex

    //println("sentenceParserUtil: $list")
    if(debug)
        println("sentenceParserUtil(${if(!selectable) "ned" else "sel"}): before $predicateContextIndex $elements $elementConditions ${types.size} $predicateSplited")

    for(index in predicateContextIndex-1 downTo 0) {
        if(types.size - predicateSplited.size == 0) break

        val pair = list[index]

        if(debug)
            println("SentenceParserUtil: $pair")

        if((types.contains(pair.second)) && (map.containsKey(pair.second) && map[pair.second]!!.contains(pair.first))) {
            map[pair.second]!!.remove(pair.first)
            if(debug)
                println("SentenceParserUtil: removed $pair")
            types.remove(pair.second)
            list.removeAt(index)
            subMap[pair.second] = pair.first
        } else if(pair.second == "서술어") {
            if(selectable)
                break
        }

    }

    //println("sentenceParserUtil: $list")
    if(debug)
        println("sentenceParserUtil(${if(!selectable) "ned" else "sel"}): doing $types ${types.size} $predicateSplited")



    if(predicateSplited.size > 0) {
        val predicateSplitedSize = predicateSplited.size
        val elementSize = elements.size
        var index = 0

        while (index < elementSize) {
            val element = elements[index]
            val elementCondition = elementConditions[index]

            if (!subMap.containsKey(element)) {
                if(debug)
                    println("sentenceparserutil: index: $index")
                val accordIndex = Stream.iterate(0, { it + 1 }).limit(predicateSplited.size.toLong()).filter { elementCondition.lambda.invoke(predicateSplited[it].source) }.limit(1).findFirst()
                if (accordIndex.isPresent) {
                    if(debug)
                        println("sentenceparserutil: accorded $accordIndex")
                    val accordIndexGet = accordIndex.get()

                    subMap[element] = predicateSplited[accordIndexGet]
                    toAddMap.computeIfAbsent(element) { ArrayList() }.add(predicateSplited[accordIndexGet])
                    predicateSplited.removeAt(accordIndexGet)
                } else {
                    if(!selectable)
                        throw RuntimeException("서술어 문장성분 검색 실패1")
                }
            }
            index++
        }
    }

    //println("sentenceParserUtil: $list")
    if(debug)
        println("sentenceParserUtil(${if(!selectable) "ned" else "sel"}): after $predicateSplited $subMap")

    return subMap
}

fun predicateIndex(list: List<Pair<Context, String>>, context: Context): Int {
    for (i in list.indices) {
        val value = list[i]
        if (value.first == context) {
            return i
        }
    }

    return -1;
}

fun getPredicateSubs(parseContext: ParseContext, map: HashMap<String, MutableList<Context>>, predicateContext: Context,
                     predicate: IPredicate, predicateSplited: MutableList<Context>, debug: Boolean, metadata: List<Any>?): Pair<HashMap<String, Context>, HashMap<String, MutableList<Context>>>? {
    val subMap = HashMap<String, Context>()
    val toAddMap = HashMap<String, MutableList<Context>>()

    //println("$map")

    val neededs = predicate.neededSentenceElements
    val selectables = predicate.optionalSentenceElements
    val neededConditions = predicate.neededSentenceElementTypes
    val selectableConditions = predicate.optionalSentenceElementTypes
    //val minimumStartRange = predicateContext.posStart
    //println("A")
    val parsedMapToIndices = parsedMapToIndices(parseContext)
    //println("B")

    //val predicateContextIndex = findPosLinear(Pair(predicateContext, ""), parsedMapToIndices)

    val currentDepth = map["서술어"]!!.indexOf(predicateContext)

    subMap.putAll(findNears(neededs, neededConditions, map, currentDepth, subMap, toAddMap, findPosLinear(Pair(predicateContext, ""), parsedMapToIndices), predicateSplited, parsedMapToIndices, false, debug))
    val count = subMap.size

    subMap.putAll(findNears(selectables, selectableConditions, map, currentDepth, subMap, toAddMap, findPosLinear(Pair(predicateContext, ""), parsedMapToIndices), predicateSplited, parsedMapToIndices, true, debug))
    val count2 = subMap.size

    // 그냥 neededs의 인덱스대로가 아니라 가까운 것부터 찾아야 하고 인덱스대로는 서술어 subs에 대해서만 인덱스를 적용시켜야 함

    /*  TODO 접속사 파싱(생략된 문장)
    TODO 만약 채워지지 못한 문장성분이 있는 경우 바로 앞 문장에서 카피해옴 - ㄱ
    TODO 채워지지 못한 문장성분중 같은 문장성분이 있으면 카피 - ㄴ
    TODO 같은 문장성분이 없으면 남은 문장성분중 "대충" 끌어옴 - ㄷ
    A가 B보다 크거나 같으면
     */

    if(count < neededs.size) { // TODO ㄱ
        val additionals = ArrayList(neededs)
        val selectables = ArrayList(selectables)
        additionals.removeIf { subMap.keys.contains(it) }
        selectables.removeIf { subMap.keys.contains(it) }

        val addSet = HashSet<String>()

        val sentence = parseContext.sentences[parseContext.sentences.size - 1]

        val sentenceToIndices = mapToIndices(sentence.map)

        additionals.forEach {// TODO ㄴ
            if(it != "서술어") {
                if(sentence.map.containsKey(it)) {
                    val context = sentence.map[it]!!

                    addSet.add(it)
                    subMap[it] = context
                }
            }
        }

        selectables.forEach {
            if(it != "서술어") {
                if(sentence.map.containsKey(it)) {
                    val context = sentence.map[it]!!

                    addSet.add(it)
                    subMap[it] = context
                }
            }
        }

        additionals.removeIf { addSet.contains(it) }
        selectables.removeIf { addSet.contains(it) }


        sentenceToIndices.forEach {// TODO ㄷ
            val nedcon = additionals.contains(it.second)
            val selcon = selectables.contains(it.second)
            if(addSet.size >= neededs.size + selectables.size)
                return@forEach

            if(it.second != "서술어" && !addSet.contains(it.second)) {
                if(debug)
                    println("SentenceParserUtil-subsequent: ${it.first} ${it.second} ${additionals.size} ${selectables.size}")
                addSet.add(it.second)
                if(additionals.size > 0) {
                    subMap[additionals.removeAt(0)] = it.first
                } else if(selectables.size > 0) {
                    subMap[selectables.removeAt(0)] = it.first
                }
            }
//            if(additionals.size == addSet.size) {
//                return@forEach
//            }
        }
    }

    if(count2 - count < selectables.size) {

    }

//    for (neededsIndex in neededs.indices) {
//        val needed = neededs[neededsIndex]
//        val elementList = map[needed]
//
//        if (elementList != null && elementList.size > 0) {
//            var index = elementList.size - 1
//            while (elementList[index].posStart >= minimumStartRange) {
//                if (index == 0) {
//                    index = -1
//                    break
//                }
//                index--
//            }
//            if (index != -1) {
//                subMap[needed] = elementList.removeAt(index)
//            }
//        } else {
//            // 해당 서술어와 관련이 없음
//        }
//    }
//
//    //println("first: $predicateSplited ${subMap.size} ${neededs.size}")
//    //println("${subMap.size} ${neededs.size}")
//
//    if (subMap.size < neededs.size) {
//        if (predicateSplited.size > 0) {
//            var neededIndex = 0
//
//            val subsSize = predicateSplited.size
//
//            while (neededIndex < subsSize) {
//                val needed = neededs[neededIndex]
//                val neededCondition = neededConditions[neededIndex]
//
//                if (!subMap.containsKey(needed)) {
//                    val accordIndex = Stream.iterate(0, { it + 1 }).filter { neededCondition.lambda.invoke(predicateSplited[it].source) }.limit(1).findFirst()
//                    if (accordIndex.isPresent) {
//                        val accordIndexGet = accordIndex.get()
//
//                        subMap[needed] = predicateSplited[accordIndexGet]
//                        toAddMap.computeIfAbsent(needed) { ArrayList() }.add(predicateSplited[accordIndexGet])
//                        predicateSplited.removeAt(accordIndexGet)
//
//                    } else {
//                        return null
//                    }
//                }
//
//                neededIndex++
//            }
//        }
//    }
//
//    for (selectablesIndex in selectables.indices) {
//        val selectable = selectables[selectablesIndex]
//        val elementList = map[selectable]
//
//        if (elementList != null && elementList.size > 0) {
//            var index = elementList.size - 1
//            while (elementList[index].posStart >= minimumStartRange) {
//                if (index == 0) {
//                    index = -1
//                    break
//                }
//                index--
//            }
//            if (index != -1)
//                subMap[selectable] = elementList[index]
//        }
//    }
//
//    //println("second: $predicateSplited ${subMap.size} ${selectables.size}")
//    if (subMap.size < neededs.size + selectables.size) {
//
//        if (predicateSplited.size > 0) {
//            var selectableIndex = 0
//            while (selectableIndex < predicateSplited.size) {
//                val selectable = selectables[selectableIndex]
//                val selectableCondition = selectableConditions[selectableIndex]
//
//                if (!subMap.containsKey(selectable)) {
//                    val accordIndex = Stream.iterate(0, { it + 1 }).filter { selectableCondition.lambda.invoke(predicateSplited[it].source) }.limit(1).findFirst()
//                    if (accordIndex.isPresent) {
//                        subMap[selectable] = predicateSplited[accordIndex.get()]
//                        toAddMap.computeIfAbsent(selectable) { ArrayList() }.add(predicateSplited[accordIndex.get()])
//                        predicateSplited.removeAt(accordIndex.get())
//                        selectableIndex--
//                    } else {
//                        return null
//                    }
//                }
//                selectableIndex++
//            }
//        }
//    }

    //println("sub: ${predicate.defaultSentence} $subMap")

    return if (predicate.isAccord(subMap, metadata)) {
        Pair(subMap, toAddMap)
    } else null

}

fun concatContexts(list: List<Context>): Context? {
    val sb = StringBuilder()
    if (list.isNotEmpty())
        sb.append(list[0].source)

    for (i in 1 until list.size) {
        sb.append(" ")
        sb.append(list[i].source)
    }

    return if (list.isNotEmpty()) {
        Context(sb.toString(), false, list[0].posStart, list[list.size - 1].posFinish)
    } else {
        null
    }
}



val predicateVerify = IParser.PostPositionVerify(JosaStorage.INSTANCE.getJosaList(JosaCommunity.격조사).asSequence().filter { it.type == "서술어"}.first(), 0)

fun splitPredicateSource2(srcContext: Context): HashMap<String, MutableList<Context>> {
    val map = HashMap<String, MutableList<Context>>()
    val start = srcContext.posStart

    var source = srcContext.source
    val result = defaultParse(source, JosaStorage.INSTANCE.getJosaList(JosaCommunity.접속조사), true, predicateVerify)
    val boeo = result["접속조사"]
    if(boeo != null) {
        var sum = 0
        for (i in boeo.indices) {
            if (i != boeo.size - 1) {
                sum++
            }
            sum += boeo[i].posFinish
        }

        val context = Context(source.substring(0, sum), false, srcContext.posStart, srcContext.posStart + sum)
        context.variables = boeo.stream().map { parseVariable(it.source) }.collect(Collectors.toList())

        //val seo = src.substring(sum + 2)
        //val context2 = Context(seo, false, srcContext.posStart + sum + 2, srcContext.posFinish)

        map.computeIfAbsent("보어") { ArrayList() }.add(context)
        //map.computeIfAbsent("서술어") { ArrayList() }.add(context2)
                //https://youtu.be/1zcdJ1m2eu4

        source = "${source.substring(sum + 2)} "
    } else {
        source = "$source "
    }
    //println(source)

    val list = ArrayList<Context>()
    val buffer = StringBuilder()

    var noParse = 0
    var index = 0

    while (index < source.length) {
        val ch = source[index]
        if (ch == '\"') {
            if (noParse == 0) {
                noParse = 1
            } else if (noParse == 1) {
                noParse = 0
            }
        } else if (ch == '\'') {
            if (noParse == 0) {
                noParse = 2
            } else if (noParse == 2) {
                noParse = 0
            }
        }
        if (ch == ' ') {
            if (noParse == 0) {
                val element = Context(buffer.toString(), false, start + index - buffer.length + list.size, start + index + list.size)

                //println("$index ${source.length}")

                if(index == source.length - 1) {
                    map["서술어"] = mutableListOf(element)
                } else {
                    list.add(element)
                }
                buffer.setLength(0)
            }
        } else {
            buffer.append(ch)
        }
        index++
    }
    map["temp"] = list

    return map
}

fun splitPredicateSource(context: Context): MutableList<Context> {
    val start = context.posStart

    val source = "${context.source} " // TODO 어휴 귀찮아
    val list = LinkedList<Context>()
    val buffer = StringBuilder()

    var noParse = 0
    var index = 0

    //val templist = ArrayList<String>()

    while (index < source.length) {
        val ch = source[index]
        if (ch == '\"') {
            if (noParse == 0) {
                noParse = 1
            } else if (noParse == 1) {
                noParse = 0
            }
        } else if (ch == '\'') {
            if (noParse == 0) {
                noParse = 2
            } else if (noParse == 2) {
                noParse = 0
            }
        }
        if (ch == ' ') {
            if (noParse == 0) {
                val element = Context(buffer.toString(), false, start + index - buffer.length, start + index)
                list.add(element)
                buffer.setLength(0)
            }
        } else {
            buffer.append(ch)
        }
        index++
    }

    return list
}