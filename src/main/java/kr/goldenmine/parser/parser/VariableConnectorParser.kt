package kr.goldenmine.parser.parser

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.ParseContext
import kr.goldenmine.parser.parser.util.parseVariableAndConnector
import kr.goldenmine.parser.postposition.JosaCommunity
import kr.goldenmine.parser.postposition.JosaStorage

class VariableConnectorParser(private val parser: IParser?) : IParser {

    override fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?) {
        parser?.parse(code, parseContext, index, debug, metadata)
        if (!parseContext.isNoParse) {
            val verify = IParser.PostPositionVerify(JosaStorage.getJosaList(JosaCommunity.접속조사)[0], 0)
            val map = parseContext.parsedMap

            val 접속조사리스트 = JosaStorage.getJosaList(JosaCommunity.접속조사)

            for (key in map.keys) {
                if (key == "서술어") {
                    continue
                }

                val value = map[key]!!

                for (i in value.indices) {
                    val context = value[i]
                    if(debug)
                        println("variableconnector: ${context.source}")
                    context.variables = parseVariableAndConnector(context)
//                    var result: List<Context>? = defaultParse(context.source, 접속조사리스트, true, predicateVerify)["접속조사"]
//                    //println(result)
//                    if (result == null) {
//                        result = ArrayList()
//                    }
//                    context.setVariable(result.stream().map { Context.parseVariable(it.source) }.collect(Collectors.toList<Variable>()))
                    //println("${context.variables} ${context.source}")
                    //System.out.println(context.getVariables() + ", " + context.getSource());
                }
            }
        }
        //        map.forEach((t, k) -> {
        //            if(t.equals("서술어")) {
        //                return;
        //            }
        //
        //
        //        });

    }
}
