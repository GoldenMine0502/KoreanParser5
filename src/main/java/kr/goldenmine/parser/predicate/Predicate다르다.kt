package kr.goldenmine.parser.predicate

import kr.goldenmine.objects.Variable
import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.VariableStorage

class Predicate다르다 : Predicate같다() {

    override val defaultSentence: String
        get() = "다르다"

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable {
        val result = super.perform(sentence, metadata, local)
        return Variable(!result.booleanValue(), result.isConst)
    }
}