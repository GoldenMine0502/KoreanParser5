package kr.goldenmine.objects.objects.defaults

import kr.goldenmine.impl.CompareException
import kr.goldenmine.objects.KoreanObject

class ObjectString(str: String) : KoreanObject {
    override fun setValue(key: String, value: KoreanObject) {

    }

    override fun setValue(index: Int, value: KoreanObject) {

    }

    internal var str: String = str

    constructor() : this("")

    override fun setRoot(value: Any) {
        this.str = value as String
    }

    override fun toString(): String = str

    override fun getValue(key: String): KoreanObject? {
        //[str]의 길이
        //[str]의 3번째 문자
        //[str]의 3번째문자
        when(key) {
            "길이" -> return ObjectInteger(str.length)
        }

        return null
//        if(key.endsWith("번째 문자열") || key.endsWith("번째문자열")) {
//            return
//        }
    }

    override fun getRoot(): Any {
        return str
    }

    override fun compareTo(other: KoreanObject): Int {
        if(other is ObjectString) {
            if(str != null && other.str != null) {
                return str!!.compareTo(other.str!!)
            }
        }

        throw CompareException("비교하려는 값이 \"문자열\"과 비교할 수 있는 값이 아닙니다.")
    }
}