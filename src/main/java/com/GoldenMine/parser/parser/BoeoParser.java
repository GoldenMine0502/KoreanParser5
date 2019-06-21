package com.GoldenMine.parser.parser;

import com.GoldenMine.parser.Code;
import com.GoldenMine.parser.Context;
import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.Sentence;
import com.GoldenMine.parser.postposition.IPostPosition;
import com.GoldenMine.parser.postposition.JosaCommunity;
import com.GoldenMine.parser.postposition.JosaStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BoeoParser implements IParser {
    private IParser parser;
    private IPostPosition postPosition;

    public BoeoParser(IParser parser) {
        this.parser = parser;

        List<? extends IPostPosition> list = JosaStorage.INSTANCE.getJosaList(JosaCommunity.격조사);
        for (IPostPosition postPosition : list) {
            if (postPosition.getType().equals("서술어")) {
                this.postPosition = postPosition;
                break;
            }
        }
    }

    @Override
    public void parse(Code code, ParseContext parseContext, int index) {
        if (parser != null) {
            parser.parse(code, parseContext, index);
        }

        if (!parseContext.isNoParse()) {
            PostPositionVerify verify = new PostPositionVerify(postPosition, 0);
            List<? extends IPostPosition> 접속조사리스트 = JosaStorage.INSTANCE.getJosaList(JosaCommunity.접속조사);

            int srcIndex = 0;

            for (Context srcContext : parseContext.getParsedMap().get("서술어")) {
                String src = srcContext.getSource();

                HashMap<String, List<Context>> result = defaultParse(src, 접속조사리스트, true, verify);
                if (result != null) {
                    List<Context> boeo = result.get("접속조사");

                    if (boeo != null) {
                        int sum = 0;
                        for (int i = 0; i < boeo.size(); i++) {
                            if (i != boeo.size() - 1) {
                                sum++;
                            }
                            sum += boeo.get(i).getPosFinish();
                        }

                        Context context = new Context(src.substring(0, sum), false, srcContext.getPosStart(), srcContext.getPosStart() + sum);
                        context.setVariable(boeo.stream().map(it -> Context.parseVariable(it.getSource())).collect(Collectors.toList()));

                        Context context2 = new Context(src.substring(sum + 1), false, srcContext.getPosStart() + sum + 1, srcContext.getPosStart() + src.length());

                        HashMap<String, List<Context>> map = parseContext.getParsedMap();

                        map.computeIfAbsent("보어", t -> new ArrayList<>()).add(context);
                        map.get("서술어").set(srcIndex, context2);

                        srcIndex++;
                    }
                }
            }
        }
    }
}
