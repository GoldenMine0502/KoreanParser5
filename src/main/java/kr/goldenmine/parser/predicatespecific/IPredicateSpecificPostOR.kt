package kr.goldenmine.parser.predicatespecific

import kr.co.shineware.nlp.komoran.model.KomoranResult
import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.Variable
import kr.goldenmine.parser.VariableStorage

class IPredicateSpecificPostOR :IPredicateSpecificPost {

    override val others: List<String>
        get() = listOf()

    override fun verify(source: String, result: KomoranResult): Boolean {
        val list = result.list

        return when(list[list.size - 1].first) {
            "거나"-> true
            else -> false
        }
    }

    override fun perform(sentence: Sentence, metadata: List<Any>?,local: VariableStorage, originalResult: MutableList<Variable?>?): Variable {
        val first = originalResult!![originalResult.size - 2]!!
        val second = originalResult[originalResult.size - 1]!!

        val variable = Variable(first.OR(second), false)

        originalResult.removeAt(originalResult.size - 1)
        originalResult.removeAt(originalResult.size - 1)
        originalResult.add(variable)

        return variable
    }

    override fun execute(line: Int, metadata: List<Any>?, sentence: Sentence, parameters: IPredicateSpecific.MultiProcessData?, results: List<Variable?>?): Int {
        return line
    }
}