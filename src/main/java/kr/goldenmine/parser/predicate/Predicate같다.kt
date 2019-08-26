package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.parser.VariableStorage
import java.util.*

class Predicate같다 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true)

    override val optionalReplaceVariable: List<Boolean>
        get() = listOf(true)

    override val neededSetters: List<Boolean>
        get() = listOf(false)

    override val optionalSetters: List<Boolean>
        get() = listOf(false)

    override val defaultSentence: String
        get() = "같다"

    override val neededSentenceElements: List<String>
        get() = listOf("주어")

    override val optionalSentenceElements: List<String>
        get() = listOf("보어")

    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.STRING)

    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.STRING)



    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable {
        val 주어 = sentence.map["주어"]!!
        val 보어 = sentence.map["보어"]

        val 주어Var = 주어.variables!!
        val main = 주어Var[0]

        //println("main: $main $주어Var ${보어!!.variables!!}")

        for(i in 1 until 주어Var.size) {
            //println("sub: ${주어Var[i]}")
            if(!main!!.equals(주어Var[i])) {
                return Variable(false, false)
            }
        }
        if(보어 != null) {
            val 보어Var = 보어.variables!!

            for(i in 0 until 보어Var.size) {
                //println("sub2: ${보어Var[i]}")
                if(!main!!.equals(보어Var[i])) {
                    return Variable(false, false)
                }
            }
        }

        return Variable(true, false)
    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean {
        return true
    }
}
