package kr.goldenmine.parser.predicatespecific

import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.Variable
import kr.goldenmine.parser.VariableStorage
import kr.co.shineware.nlp.komoran.model.KomoranResult
import java.util.*

class SpecificIF : IPredicateSpecific {
    override val others: List<String>
        get() = Arrays.asList("그렇지 않으면", "조건문의 끝")

    override fun execute(line: Int,  metadata: List<Any>?, sentence: Sentence,parameters: IPredicateSpecific.MultiProcessData?, results: List<Variable?>?): Int {
        //println("executed $line ${parameters!!.getAppliedValue(1)} ${results != null}")
        if (parameters != null && parameters.size > 0) {
            //println("executedd $line")
            if (results != null && line == parameters.getAppliedValue(0)) {
                val result = results[results.size - 1]

                if (result != null) {
                    val istrue = result.isTrue
                    parameters.meta = istrue

                    if (istrue) {
                        return line
                    } else {
                        val elseParam = parameters.getAppliedValue(1)
                        return if (elseParam != -1) {
                            elseParam
                        } else {
                            parameters.getAppliedValue(2)
                        }
                    }
                }
            } else if (line == parameters.getAppliedValue(1)) {
                val istrue = parameters.meta as Boolean

                if (istrue) {
                    return parameters.getAppliedValue(2)
                }
            }
        }
        return line
    }

    override fun verify(source: String, result: KomoranResult): Boolean {
        val list = result.list
        val last = list[list.size - 1]

        when (last.first) {
            "라면", "ㄴ다면", "다면", "자면", "면", "으면" -> return true
        }

        return false
    }

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage, originalResult: MutableList<Variable?>?): Variable? {
        return originalResult!![originalResult.size - 1]
    }
}
