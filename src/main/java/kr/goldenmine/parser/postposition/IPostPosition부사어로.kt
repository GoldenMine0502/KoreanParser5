package kr.goldenmine.parser.postposition

class IPostPosition부사어로 : IPostPosition {
    override val type: String
        get() = "부사어로"

    override val josaCommunity: JosaCommunity
        get() = JosaCommunity.격조사

    override fun verify(last: CharSequence): Int {
        if (last.length >= 2) {
            val ch = last[last.length - 2]
            val ch2 = last[last.length - 1]

            return if (ch == '으' && ch2 == '로') 2 else if (ch2 == '로') 1 else -1
        } else if (last.length >= 1) {
            val ch2 = last[last.length - 1]

            return if (ch2 == '로') 1 else -1
        } else {
            return -1
        }
    }
}
