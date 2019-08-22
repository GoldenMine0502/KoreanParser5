package kr.goldenmine.parser.pronoun

import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.Variable
import kr.goldenmine.parser.VariableStorage

interface IPronoun {
    val type: String
    fun perform(sentence: Sentence, metadata: List<Any>?, storage: VariableStorage): Variable?
}
