package com.GoldenMine.parser.postposition;

public class IPostPosition부사어로 implements IPostPosition {
    @Override
    public String getType() {
        return "부사어로";
    }

    @Override
    public int verify(CharSequence last) {
        if(last.length() >= 2) {
            char ch = last.charAt(last.length() - 2);
            char ch2 = last.charAt(last.length() - 1);

            return (ch == '으' && ch2 == '로') ? 2 : ((ch2 == '로') ? 1 : -1);
        } else if(last.length() >= 1){
            char ch2 = last.charAt(last.length() - 1);

            return  ((ch2 == '로') ? 1 : -1);
        } else {
            return -1;
        }
    }

    @Override
    public JosaCommunity getJosaCommunity() {
        return JosaCommunity.격조사;
    }
}
