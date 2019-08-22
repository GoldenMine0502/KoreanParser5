package kr.goldenmine.parser.postposition

class IPostPosition까지 : IPostPosition {
    override val type: String
        get() = "까지"

    override val josaCommunity: JosaCommunity
        get() = JosaCommunity.보조사

    override fun verify(last: CharSequence): Int {
        return if (last.length >= 2) {
            if (last[last.length - 2] == '까' && last[last.length - 1] == '지') 2 else -1
        } else -1
    }
}
