package com.GoldenMine.parser.postposition;

public interface IPostPosition {
    String getType();
    int verify(CharSequence last);
    JosaCommunity getJosaCommunity();
}
