package com.GoldenMine.parser.postposition;

public class IPostPosition부사어에서 implements IPostPosition {
    @Override
    public String getType() {
        return "부사어에서";
    }

    @Override
    public int verify(CharSequence last) {
        if(last.length() >= 2) {

            char ch = last.charAt(last.length() - 2);
            char ch2 = last.charAt(last.length() - 1);

            return (ch == '에' && ch2 =='서') ? 2 : -1;
        } else {
            return -1;
        }
    }

    @Override
    public JosaCommunity getJosaCommunity() {
        return JosaCommunity.격조사;
    }
}
