package kr.goldenmine.parser.postposition

class IPostPosition주어 : IPostPosition {
    override val type: String
        get() = "주어"

    override val josaCommunity: JosaCommunity
        get() = JosaCommunity.격조사

    override fun verify(last: CharSequence): Int {
        if (last.length >= 2) {
            val ch2 = last[last.length - 1]
            val ch = last[last.length - 2]

            //System.out.println(ch + " " + ch2);

            return if (ch2 == '이' || ch2 == '가') 1 else if ((ch == '께' || ch == '꼐') && ch2 == '서') 2 else -1
        } else if (last.length == 1) {
            val ch2 = last[last.length - 1]

            return if (ch2 == '이' || ch2 == '가') 1 else -1
        } else {
            return -1
        }
    }
}
