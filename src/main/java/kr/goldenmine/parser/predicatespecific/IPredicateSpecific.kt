package kr.goldenmine.parser.predicatespecific

import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.Variable
import kr.goldenmine.parser.VariableStorage
import kr.co.shineware.nlp.komoran.model.KomoranResult

interface IPredicateSpecific {

    val others: List<String>
    fun verify(source: String, result: KomoranResult): Boolean

    fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage, originalResult: MutableList<Variable?>?): Variable?

    fun execute(line: Int, metadata: List<Any>?, sentence: Sentence, parameters: MultiProcessData?, results: List<Variable?>?): Int

    class MultiProcessData(private val applyLine: List<Int>) {
        var meta: Any? = null

        val size: Int
            get() = applyLine.size

        fun getAppliedValue(index: Int): Int {
            return applyLine[index]
        }

        override fun toString(): String {
            return "$applyLine, $meta"
        }
    }
}
