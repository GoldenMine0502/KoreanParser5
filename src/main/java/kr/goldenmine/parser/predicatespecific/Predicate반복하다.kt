package kr.goldenmine.parser.predicatespecific

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.Variable
import kr.goldenmine.parser.VariableStorage
import kr.goldenmine.parser.predicate.IPredicate
import kr.goldenmine.parser.predicate.PredicateStorage
import kr.goldenmine.parser.predicate.ValueScope
import kr.co.shineware.nlp.komoran.model.KomoranResult
import java.util.*

class Predicate반복하다 : IPredicate, IPredicateSpecific {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf()
    override val optionalReplaceVariable: List<Boolean>
        get() = listOf(true)
    override val defaultSentence: String
        get() = "반복합니다"

    override val neededSentenceElements: List<String>
        get() = emptyList()

    override val optionalSentenceElements: List<String>
        get() = listOf("까지")

    override val others: List<String>
        get() = listOf("반복문의 끝")

    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf()

    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.STRING)

    override fun perform(sentence: Sentence, metadata: List<Any>?,local: VariableStorage): Variable? { // void 메소드
        val x = sentence.map["까지"]

        if(x != null) {
            val value = x.variables!![0]
                    ?: throw RuntimeException("\"까지\"보조사에 값이 존재하지 않습니다. (null입니다)")
            //println("까지: ${x.variables!![0]}")

            return Variable(!value.booleanValue(), false)
        } else {
            return null
        }
    }

    override fun verify(source: String, result: KomoranResult): Boolean {
        return result.list[0].first == PredicateStorage.getRoot(defaultSentence)
    }

    override fun perform(sentence: Sentence, metadata: List<Any>?,local: VariableStorage, originalResult: MutableList<Variable?>?): Variable? {
        //println(originalResult != null)
        return originalResult!![originalResult.size - 1]
    }

    override fun execute(line: Int, metadata: List<Any>?, sentence: Sentence, parameters: IPredicateSpecific.MultiProcessData?, results: List<Variable?>?): Int {
        if(parameters != null) {
            if (line == parameters.getAppliedValue(0)) {
                if(results != null) {
                    val result2 = results[results.size - 1]

                    if (result2 != null) {
                        //println("result2:  " + result2.isTrue)
                        if (!result2.isTrue) {
                            return parameters.getAppliedValue(1)
                        }
                    } else {
                        if (results.size >= 2) {
                            val result = results[results.size - 2]

                            if (result != null) {
                                //println("result:  " + result.isTrue)
                                if (!result.isTrue) {
                                    //println("${parameters.getAppliedValue(0)}, ${parameters.getAppliedValue(1)}")
                                    return parameters.getAppliedValue(1)
                                }
                            }
                        }
                    }
                }
            } else if (line == parameters.getAppliedValue(1)) {
                return parameters.getAppliedValue(0) - 1
            }
        }
        return line
    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean {
        return true
    }
}
