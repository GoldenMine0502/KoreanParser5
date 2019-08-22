package kr.goldenmine.`object`

interface KoreanObject : Comparable<KoreanObject> {
    fun getValue(key: String): Any?
    fun setValue(key: String, value: Any?)
    fun setValue(index: Int, value: Any?)
    /*
    DB 고전
    정보보호 확통
    웹플 공수 심화영어
    PBE 앱제작
     */
}
