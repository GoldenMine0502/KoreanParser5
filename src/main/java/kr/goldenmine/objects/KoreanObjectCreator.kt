package kr.goldenmine.objects

interface KoreanObjectCreator {
    val type: String

    // [i] = long
    // [d] = double
    // [s] = string
    // [b] = boolean
    // [i], [d] = long, double을 파라미터로 받음.
    // [i] and [d] = and를 쓰면 접속조사와 같은 것으로 생각.
    val regex: String

    val instance: KoreanObject
    fun getValueType(key: String): VariableMode
}
