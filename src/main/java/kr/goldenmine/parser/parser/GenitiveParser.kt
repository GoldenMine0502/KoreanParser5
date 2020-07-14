package kr.goldenmine.parser.parser

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.ParseContext
import kr.goldenmine.objects.Variable
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.parser.parseVariable
import kr.goldenmine.parser.postposition.JosaCommunity
import kr.goldenmine.parser.postposition.JosaStorage

class GenitiveParser(val parser: IParser?) : IParser {
    /*
    TODO 파싱순서
    TODO '의' defaultParse - ㄱ
    TODO List<ㄱ> in Context - ㄴ
    TODO VariableConnectorParser - ㄷ(수정필요)
    TODO
     */
    override fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?) {
        parser?.parse(code, parseContext, index, debug, metadata)

        val josa = JosaStorage.getJosaList(JosaCommunity.소유격조사)
        val verify = IParser.PostPositionVerify(JosaStorage.getJosaList(JosaCommunity.분류없음)[0], 0)

        parseContext.parsedMap.forEach { (type, u) ->
            if (type != "서술어") {
                u.forEach {
                    val list = ArrayList<MutableList<Variable>?>()

                    if(debug)
                        println("genitiveparser: to process $it")

                    it.variables!!.forEach { variable ->
                        if (variable != null && variable.mode == VariableMode.STRING_MODE) {
                            val map = defaultParse(variable.stringValue(), josa, true, verify)
                            if (map.containsKey("소유격")) {
                                val genitive = map["소유격"]!!
                                genitive.add(map["분류없음"]!![0])

                                list.add(genitive.asSequence().map { context -> parseVariable(context.source) }.toCollection(ArrayList()))
                            } else {
                                list.add(null)
                            }
                        } else {
                            list.add(null)
                        }
                    }

                    it.genitiveList = list
                }
            }
        }
    }

}