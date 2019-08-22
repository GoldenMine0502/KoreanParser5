package kr.goldenmine.parser.postposition

class IPostPosition분류없음 : IPostPosition {
    override val type: String
        get() = "분류없음"
    override val josaCommunity: JosaCommunity
        get() = JosaCommunity.분류없음

    override fun verify(last: CharSequence): Int {
        return -1
    }

}