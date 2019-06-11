package com.GoldenMine.parser.postposition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JosaStorage {
    public static final JosaStorage INSTANCE = new JosaStorage();

    private HashMap<JosaCommunity, List<IPostPosition>> josaMap = new HashMap<>();
    private List<IPostPosition> allJosaList = new ArrayList<>();
    private JosaStorage() {
        init();
    }

    private void init() {
        addJosa(new IPostPosition주어());
        addJosa(new IPostPosition목적어());
        addJosa(new IPostPosition서술어());
        addJosa(new IPostPosition접속조사());
        addJosa(new IPostPosition부사어로());
        addJosa(new IPostPosition부사어에());
        addJosa(new IPostPosition부사어에서());
    }

    public List<? extends IPostPosition> getAllJosaList() {
        return allJosaList;
    }

    public List<? extends IPostPosition> getJosaList(JosaCommunity community) {
        return josaMap.get(community);
    }

    public void addJosa(IPostPosition josa) {
        List<IPostPosition> josaList;
        if(josaMap.containsKey(josa.getJosaCommunity())) {
            josaList = josaMap.get(josa.getJosaCommunity());
        } else {
            josaList = new ArrayList<>();
            josaMap.put(josa.getJosaCommunity(), josaList);
        }

        josaList.add(josa);
        allJosaList.add(josa);
    }
}
