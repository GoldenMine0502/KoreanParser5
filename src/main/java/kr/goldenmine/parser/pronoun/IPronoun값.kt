package kr.goldenmine.parser.pronoun

import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.parser.VariableStorage

class IPronoun값 : IPronoun {

    override val type: String
        get() = "값"

    override fun perform(sentence: Sentence,metadata: List<Any>?, storage: VariableStorage): Variable? {
        return sentence.서술어.perform(sentence, metadata, storage)
    }
}
