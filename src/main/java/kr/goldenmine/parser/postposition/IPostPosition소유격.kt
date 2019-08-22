package kr.goldenmine.parser.postposition

class IPostPosition소유격 : IPostPosition {
    override val type: String
        get() = "소유격"
    override val josaCommunity: JosaCommunity
        get() = JosaCommunity.소유격조사

    override fun verify(last: CharSequence): Int {
        return if(last.isNotEmpty()) if(last[last.length - 1] == '의') 1 else -1 else -1
    }
}