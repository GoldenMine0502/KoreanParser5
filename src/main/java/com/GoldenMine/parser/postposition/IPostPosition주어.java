package com.GoldenMine.parser.postposition;

public class IPostPosition주어 implements IPostPosition {
    @Override
    public String getType() {
        return "주어";
    }

    @Override
    public int verify(CharSequence last) {
        char ch2 = last.charAt(last.length() - 1);
        char ch = last.charAt(last.length() - 2);

        //System.out.println(ch + " " + ch2);

        return (ch2 == '이' || ch2 == '가') ? 1 : (((ch == '께' || ch == '꼐') && ch2 =='서') ? 2 : -1);
    }

    @Override
    public JosaCommunity getJosaCommunity() {
        return JosaCommunity.격조사;
    }
}
