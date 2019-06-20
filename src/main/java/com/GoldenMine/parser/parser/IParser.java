package com.GoldenMine.parser.parser;

import com.GoldenMine.parser.Code;
import com.GoldenMine.parser.Context;
import com.GoldenMine.parser.ParseContext;
import com.GoldenMine.parser.postposition.IPostPosition;
import com.GoldenMine.parser.postposition.JosaStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IParser { // ParseContext객체를 데코레이팅
    // A가 B와 같을 때까지 반복합니다
    // 까지 = 보조사
    // 때 = 대명사
    // 때가 false이면 뒷문장을 반복함


    void parse(Code code, ParseContext context, int index);

    default HashMap<String, List<Context>> defaultParse(String source, List<? extends IPostPosition> josaList, boolean bufferProcess, PostPositionVerify bufferVerify) {
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
                PostPositionVerify josaVerify;
                if(i == source.length() -1 && bufferProcess) {
                    josaVerify = bufferVerify;
                } else {
                    josaVerify = getPostPosition(buffer, josaList);
                }

                IPostPosition josa = josaVerify.getJosa();

                if (josa != null) {
                    int verify = josaVerify.getVerify();

                    successParse = true;
                    buffer.setLength(buffer.length() - verify);
                    map.computeIfAbsent(josa.getType(), t -> new ArrayList<>()).add(new Context(buffer.toString(), false, start, finish - verify)); // 파싱결과 맵에 추가
                    buffer.setLength(0); // clear

                    start = i + 1;
                }

//                PostPositionVerify all = getPostPosition(buffer, JosaStorage.INSTANCE.getAllJosaList());
//                if(all.getJosa() == null) {
//                    buffer.setLength(0);
//                }
            }
            if (!successParse) {
                buffer.append(ch);
            }

        }

        return map;
    }

    default PostPositionVerify getPostPosition(CharSequence buffer, List<? extends IPostPosition> josaList) {
        IPostPosition josa = null;
        int verify = 0;
        for (IPostPosition josaToVerify : josaList) {
            verify = josaToVerify.verify(buffer);
            if (verify >= 0) {
                josa = josaToVerify;
                break;
            }
        }

        return new PostPositionVerify(josa, verify);
    }

    class PostPositionVerify {
        private IPostPosition josa;
        private int verify;

        public PostPositionVerify(IPostPosition josa, int verify) {
            this.josa = josa;
            this.verify = verify;
        }

        public IPostPosition getJosa() {
            return josa;
        }

        public int getVerify() {
            return verify;
        }
    }
}
