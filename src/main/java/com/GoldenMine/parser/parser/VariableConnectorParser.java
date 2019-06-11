package com.GoldenMine.parser.parser;

import com.GoldenMine.parser.Context;
import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.postposition.IPostPosition;
import com.GoldenMine.parser.postposition.JosaCommunity;
import com.GoldenMine.parser.postposition.JosaStorage;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class VariableConnectorParser implements IParser {
    private IParser parser;

    public VariableConnectorParser(IParser parser) {
        this.parser = parser;
    }

    @Override
    public void parse(ParseContext parseContext) {
        if(parser != null)
            parser.parse(parseContext);

        HashMap<String, List<Context>> map = parseContext.getParsedMap();
        //System.out.println(map);
        List<? extends IPostPosition> 접속조사리스트 = JosaStorage.INSTANCE.getJosaList(JosaCommunity.접속조사);

        for(String key : map.keySet()) {
            if(key.equals("서술어")) {
                continue;
            }

            List<Context> value = map.get(key);

            for(int i = 0; i < value.size(); i++) {
                Context context = value.get(i);
                List<Context> result = defaultParse(context.getSource(), 접속조사리스트).get("접속조사");
                if(result == null) {
                    result = Collections.singletonList(value.get(i));
                }
                context.setVariable(result.stream().map(it -> Context.parseVariable(it.getSource())).collect(Collectors.toList()));
                //System.out.println(context.getVariables() + ", " + context.getSource());
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
