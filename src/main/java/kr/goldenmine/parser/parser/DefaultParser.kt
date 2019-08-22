package kr.goldenmine.parser.parser

import kr.goldenmine.parser.Code
import kr.goldenmine.parser.ParseContext
import kr.goldenmine.parser.postposition.JosaCommunity
import kr.goldenmine.parser.postposition.JosaStorage
import java.util.*

class DefaultParser(private val childParser: IParser?) : IParser {


    override fun parse(code: Code, parseContext: ParseContext, index: Int, debug: Boolean, metadata: List<Any>?) {
        childParser?.parse(code, parseContext, index, debug, metadata)
        if (!parseContext.isNoParse) {
            val source = parseContext.source
            val 격조사리스트 = ArrayList(JosaStorage.INSTANCE.getJosaList(JosaCommunity.격조사))
            격조사리스트.addAll(JosaStorage.INSTANCE.getJosaList(JosaCommunity.보조사))

            parseContext.parsedMap.putAll(defaultParse(source, 격조사리스트, false, null))
            //println(parseContext.parsedMap)
            //System.out.println(parseContext.getParsedMap());
        }
    }

    //    private void addToContext(ParseContext context, IPostPosition postposition, String result) {
    //        HashMap<String, List<Context>> map = context.getParsedMap();
    //
    //        List<Context> contextList;
    //        if(map.containsKey(postposition.getType())) {
    //            contextList = map.get(postposition.getType());
    //        } else {
    //            contextList = new ArrayList<>();
    //            map.put(postposition.getType(), contextList);
    //        }
    //
    //        contextList.add(new Context(result));
    //    }
}
