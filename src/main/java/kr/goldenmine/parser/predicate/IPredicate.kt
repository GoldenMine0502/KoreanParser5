package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.parser.VariableStorage
import java.util.*

interface IPredicate {
    val defaultSentence: String

    val neededSentenceElements: List<String>
    val neededSentenceElementTypes: List<ValueScope>
    val neededReplaceVariable: List<Boolean>
    val neededSetters: List<Boolean>

    val optionalSentenceElements: List<String>
    val optionalSentenceElementTypes: List<ValueScope>
    val optionalReplaceVariable: List<Boolean>
    val optionalSetters: List<Boolean>

    fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable?

    fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean
    //    List<VariableVerify> preInterpret(KoreanProgram program, Sentence sentence, HashMap<String, List<Element>> parameters);
    //
    //    VariableResult execute(KoreanProgram program, Sentence sentence, HashMap<String, List<Element>> parameters);

}
