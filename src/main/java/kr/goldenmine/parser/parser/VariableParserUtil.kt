package kr.goldenmine.parser.parser

import kr.goldenmine.parser.Context
import kr.goldenmine.objects.Variable
import kr.goldenmine.parser.parseVariable
import kr.goldenmine.parser.postposition.JosaCommunity
import kr.goldenmine.parser.postposition.JosaStorage
import java.util.ArrayList
import java.util.stream.Collectors

fun parseVariableAndConnector(context: Context): MutableList<Variable?> {
    val 접속조사리스트 = JosaStorage.INSTANCE.getJosaList(JosaCommunity.접속조사)
    val verify = IParser.PostPositionVerify(접속조사리스트[0], 0)

    var result: List<Context>? = defaultParse(context.source, 접속조사리스트, true, verify)["접속조사"]

    if (result == null) {
        result = ArrayList()
    }
    //println(result)
    return result.stream().map { parseVariable(it.source) }.collect(Collectors.toList())
}