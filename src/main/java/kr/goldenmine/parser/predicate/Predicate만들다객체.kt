package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.Variable
import kr.goldenmine.parser.VariableStorage
import java.util.*

class Predicate만들다객체 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true, true)
    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val defaultSentence: String
        get() = "만들다"

    override val neededSentenceElements: List<String>
        get() = listOf("부사어로", "목적어")

    override val optionalSentenceElements: List<String>
        get() = emptyList()

    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.STRING, ValueScope.VARIABLE)

    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf()

    // a, b로 xy좌표를 만든 것을 A에 저장합니다
    // 문장성분이 존재하지 않으면 조사생략 -> 앞문장 전체

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable? {

        return null
    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean {
        return true
    }
}
