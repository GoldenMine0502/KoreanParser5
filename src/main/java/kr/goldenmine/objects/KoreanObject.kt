package kr.goldenmine.objects

// 내부적으로 null은 피하도록 한다

interface KoreanObject : Comparable<KoreanObject> {
    fun getRoot(): Any
    fun setRoot(value: Any)
    fun getValue(key: String): KoreanObject? // null 반환시 에러 발생
    fun setValue(key: String, value: KoreanObject) // null로 set 불가
    fun setValue(index: Int, value: KoreanObject) // null로 set 불가
    /*
    DB 고전
    정보보호 확통
    웹플 공수 심화영어
    PBE 앱제작
     */

    override fun toString(): String
}
