package kr.goldenmine.parser.postposition

class IPostPosition부사어에서 : IPostPosition {
    override val type: String
        get() = "부사어에서"

    override val josaCommunity: JosaCommunity
        get() = JosaCommunity.격조사

    override fun verify(last: CharSequence): Int {
        if (last.length >= 2) {

            val ch = last[last.length - 2]
            val ch2 = last[last.length - 1]

            return if (ch == '에' && ch2 == '서') 2 else -1
        } else {
            return -1
        }
    }
}
