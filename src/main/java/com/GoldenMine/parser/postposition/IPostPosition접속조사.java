package com.GoldenMine.parser.postposition;

public class IPostPosition접속조사 implements IPostPosition {
    @Override
    public String getType() {
        return "접속조사";
    }

    // 나는 형과 동생과 산책을 갔다.
    // 만약 C와 A가 B와 같다면


    @Override
    public int verify(CharSequence last) {
        char ch = last.charAt(last.length() - 1);

        //System.out.println(ch);

        return (ch == '와' || ch == '과') ? 1 : -1;
    }

    @Override
    public JosaCommunity getJosaCommunity() {
        return JosaCommunity.접속조사;
    }
}
