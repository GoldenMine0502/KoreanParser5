package com.GoldenMine.parser.parser;

import com.GoldenMine.parser.Context;
import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.postposition.IPostPosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IParser { // ParseContext객체를 데코레이팅
    void parse(ParseContext context);

    default HashMap<String, List<Context>> defaultParse(String source, List<? extends IPostPosition> josaList) {
        HashMap<String, List<Context>> map = new HashMap<>();

        StringBuilder buffer = new StringBuilder();
        int noParse = 0;

        int start = 0;
        int finish = 0;

        for (int i = 0; i < source.length(); i++) {
            char ch = source.charAt(i);
            boolean parseAvailable = false;
            boolean successParse = false;

            if (ch == '\"') {
                if (noParse == 0) {
                    noParse = 1;
                } else if (noParse == 1) {
                    noParse = 0;
                }
            }
            if (ch == '\'') {
                if (noParse == 0) {
                    noParse = 2;
                } else if (noParse == 2) {
                    noParse = 0;
                }
            }

            if (noParse == 0) {
                if (ch == ' ') {
                    parseAvailable = true;
                    finish = i;
                }
                if (i == source.length() - 1) {
                    buffer.append(ch);
                    parseAvailable = true;
                    finish = i+1;
                }
            }

            if (parseAvailable) {
                IPostPosition josa = null;
                int verify = 0;
                for (IPostPosition josaToVerify : josaList) {
                    verify = josaToVerify.verify(buffer);
                    if (verify >= 0) {
                        josa = josaToVerify;
                        break;
                    }
                }

                if (josa != null) {
                    successParse = true;
                    buffer.setLength(buffer.length() - verify);
                    map.computeIfAbsent(josa.getType(), t -> new ArrayList<>()).add(new Context(buffer.toString(), false, start, finish - verify)); // 파싱결과 맵에 추가
                    buffer.setLength(0); // clear

                    start = i + 1;
                }
            }
            if (!successParse) {
                buffer.append(ch);
            }
            //System.out.println(map);
        }

        return map;
    }
}
