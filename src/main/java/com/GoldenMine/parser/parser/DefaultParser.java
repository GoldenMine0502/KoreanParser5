package com.GoldenMine.parser.parser;

import com.GoldenMine.parser.Code;
import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.postposition.IPostPosition;
import com.GoldenMine.parser.postposition.JosaCommunity;
import com.GoldenMine.parser.postposition.JosaStorage;
import java.util.List;

public class DefaultParser implements IParser {
    private IParser childParser;

    public DefaultParser(IParser childParser) {
        this.childParser = childParser;
    }



    @Override
    public void parse(Code code, ParseContext context, int index) {
        if (childParser != null)
            childParser.parse(code, context, index);
        if(!context.isNoParse()) {
            String source = context.getSource();
            List<? extends IPostPosition> 격조사리스트 = JosaStorage.INSTANCE.getJosaList(JosaCommunity.격조사);

            context.getParsedMap().putAll(defaultParse(source, 격조사리스트, false, null));

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
