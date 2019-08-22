package kr.goldenmine.parser.postposition

class IPostPosition목적어 : IPostPosition {
    override val type: String
        get() = "목적어"

    override val josaCommunity: JosaCommunity
        get() = JosaCommunity.격조사

    override fun verify(last: CharSequence): Int {
        if (last.length >= 2) {
            val ch = last[last.length - 2]
            val ch2 = last[last.length - 1]

            return if (ch2 == '을' || ch2 == '를') 1 else if (ch == '만' && ch2 == '큼') 2 else -1

        } else if (last.length >= 1) {
            val ch2 = last[last.length - 1]

            return if (ch2 == '을' || ch2 == '를') 1 else -1
        } else {
            return -1
        }
    }
}
