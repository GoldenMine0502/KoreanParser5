package kr.goldenmine.parser.postposition

class IPostPosition접속조사 : IPostPosition {
    override val type: String
        get() = "접속조사"

    override val josaCommunity: JosaCommunity
        get() = JosaCommunity.접속조사

    // 나는 형과 동생과 산책을 갔다.
    // 만약 C와 A가 B와 같다면


    override fun verify(last: CharSequence): Int {
        val ch = last[last.length - 1]

        //System.out.println(ch);

        return if (ch == '와' || ch == '과') 1 else -1
    }
}
