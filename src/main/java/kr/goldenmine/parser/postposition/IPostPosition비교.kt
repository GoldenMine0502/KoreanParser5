package kr.goldenmine.parser.postposition

class IPostPosition비교 : IPostPosition {
    override val type: String
        get() = "비교"
    override val josaCommunity: JosaCommunity
        get() = JosaCommunity.격조사
    override fun verify(last: CharSequence): Int {
        return if (last.length >= 2) {
            if (last[last.length - 2] == '보' && last[last.length - 1] == '다') 2 else -1
        } else -1
    }
}