package kr.goldenmine.parser.postposition

interface IPostPosition {
    val type: String
    val josaCommunity: JosaCommunity
    fun verify(last: CharSequence): Int
}
