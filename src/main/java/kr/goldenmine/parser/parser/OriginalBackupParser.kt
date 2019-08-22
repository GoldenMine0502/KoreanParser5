package kr.goldenmine.parser.parser

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.Context
import kr.goldenmine.parser.ParseContext
import kr.goldenmine.parser.Variable

class OriginalBackupParser(val parser: IParser?) : IParser {
    override fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?) {
        parser?.parse(code, parseContext, index, debug, metadata)

        parseContext.sentences.forEach {
            it.map.forEach { (t, u) ->
                val newContext = Context(u)
                val newVariables = ArrayList<Variable?>()
                u.variables!!.forEach {
                    //println(it!!.get())
                    newVariables.add(Variable(it!!))
                }

                newContext.setVariable(newVariables)
                it.originalMap[t] = newContext
            }
        }
    }

}