package kr.goldenmine.objects.objects.defaults

import kr.goldenmine.impl.CompareException
import kr.goldenmine.objects.KoreanObject

class ObjectString(str: String) : KoreanObject {
    override fun clone(): KoreanObject {
        return ObjectString(value)
    }

    override fun setValue(key: String, value: KoreanObject) {

    }

    override fun setValue(index: Int, value: KoreanObject) {

    }

    override fun add(other: KoreanObject): KoreanObject {
        if (other is ObjectString) {
            return ObjectString(StringBuilder(value).append(other.value).toString())
        }
        if (other is ObjectBoolean) {
            return ObjectString(StringBuilder(value).append(other.value).toString())
        }
        if (other is ObjectInteger) {
            return ObjectString(StringBuilder(value).append(other.value).toString())
        }
        if (other is ObjectDouble) {
            return ObjectString(StringBuilder(value).append(other.value).toString())
        }
//        if (other is ObjectChar) {
//            return ObjectString(StringBuilder(str).append(other.value).toString())
//        }
        return super.add(other)
    }

    internal var value: String = str

    constructor() : this("")

    override fun setRoot(value: Any) {
        //println("root: $value")
        this.value = value as String
    }

    override fun toString(): String = value

    override fun getValue(key: String): KoreanObject? {
        //[str]의 길이
        //[str]의 3번째 문자
        //[str]의 3번째문자
        when(key) {
            "길이" -> return ObjectInteger(value.length)
        }

        return null
//        if(key.endsWith("번째 문자열") || key.endsWith("번째문자열")) {
//            return
//        }
    }

    override fun getRoot(): Any {
        return value
    }

    override fun compareTo(other: KoreanObject): Int {
        if(other is ObjectString) {
            return value.compareTo(other.value)
        }

        throw CompareException("비교하려는 값이 \"문자열\"과 비교할 수 있는 값이 아닙니다.")
    }
}