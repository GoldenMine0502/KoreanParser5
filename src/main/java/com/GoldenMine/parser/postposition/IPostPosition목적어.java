package com.GoldenMine.parser.postposition;

public class IPostPosition목적어 implements IPostPosition {
    @Override
    public String getType() {
        return "목적어";
    }

    @Override
    public int verify(CharSequence last) {
        char ch = last.charAt(last.length() - 2);
        char ch2 = last.charAt(last.length() - 1);

        return (ch2 == '을' || ch2 == '를') ? 1 : ((ch == '만' && ch2 == '큼') ? 2 : -1);
    }

    @Override
    public JosaCommunity getJosaCommunity() {
        return JosaCommunity.격조사;
    }
}
