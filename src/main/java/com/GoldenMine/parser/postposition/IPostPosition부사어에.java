package com.GoldenMine.parser.postposition;

public class IPostPosition부사어에 implements IPostPosition {
    @Override
    public String getType() {
        return "부사어에";
    }

    @Override
    public int verify(CharSequence last) {
        char ch = last.charAt(last.length() - 1);
        //System.out.println("V: " + ch);
        return (ch == '에') ? 1 : -1;
    }

    @Override
    public JosaCommunity getJosaCommunity() {
        return JosaCommunity.격조사;
    }
}
