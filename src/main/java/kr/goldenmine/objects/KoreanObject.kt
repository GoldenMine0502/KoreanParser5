package kr.goldenmine.objects

// 내부적으로 null은 피하도록 한다

interface KoreanObject : Comparable<KoreanObject>, Cloneable {
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

    override public fun clone(): KoreanObject

    fun add(other: KoreanObject): KoreanObject {
        throw UnsupportedOperationException()
    }


    fun sub(other: KoreanObject): KoreanObject {
        throw UnsupportedOperationException()
    }

    fun mul(other: KoreanObject): KoreanObject {
        throw UnsupportedOperationException()
    }

    fun div(other: KoreanObject): KoreanObject {
        throw UnsupportedOperationException()
    }

    fun mod(other: KoreanObject): KoreanObject {
        throw UnsupportedOperationException()
    }

    fun and(other: KoreanObject): KoreanObject {
        throw UnsupportedOperationException()
    }

    fun or(other: KoreanObject): KoreanObject {
        throw UnsupportedOperationException()
    }

    fun xor(other: KoreanObject): KoreanObject {
        throw UnsupportedOperationException()
    }
    

    override fun toString(): String
}
