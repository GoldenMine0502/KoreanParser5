package kr.goldenmine.parser.pronoun

import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.Variable
import kr.goldenmine.parser.VariableStorage

class IPronoun때 : IPronoun {
    override val type: String
        get() = "때"

    override fun perform(sentence: Sentence, metadata: List<Any>?,storage: VariableStorage): Variable? {
        return sentence.서술어.perform(sentence, metadata, storage)
    }
}
