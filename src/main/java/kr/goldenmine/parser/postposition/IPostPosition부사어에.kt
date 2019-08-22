package kr.goldenmine.parser.postposition

class IPostPosition부사어에 : IPostPosition {
    override val type: String
        get() = "부사어에"

    override val josaCommunity: JosaCommunity
        get() = JosaCommunity.격조사

    override fun verify(last: CharSequence): Int {
        if (last.length >= 1) {
            val ch = last[last.length - 1]
            //System.out.println("V: " + ch);
            return if (ch == '에') 1 else -1
        } else {
            return -1
        }
    }
}
