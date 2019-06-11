package com.GoldenMine.parser.postposition;

import java.util.List;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.util.common.model.Pair;

public class IPostPosition서술어 implements IPostPosition {
    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
    {
        komoran.setFWDic("user_data/fwd.user");
        komoran.setUserDic("user_data/dic.user");
    }

    @Override
    public String getType() {
        return "서술어";
    }

    @Override
    public int verify(CharSequence lastSeq) {
        String lastToStr = lastSeq.toString();

        KomoranResult analyzeResultList = komoran.analyze(lastToStr);
        List<Pair<String, String>> list = analyzeResultList.getList();
        //Pair<String, String> first = list.get(0);
        Pair<String, String> last = list.get(list.size() - 1);

        switch (last.getSecond()) {
            case "EC":
            case "ETM":
            case "ETN":
            case "JX":
                return 0;
            default:
                return -1;
        }
        //같다면
        //더한
        //뺀
        //3이라면



        //return 0;
    }

    @Override
    public JosaCommunity getJosaCommunity() {
        return JosaCommunity.격조사;
    }
}
