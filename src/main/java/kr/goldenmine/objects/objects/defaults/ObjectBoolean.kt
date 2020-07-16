package kr.goldenmine.objects.objects.defaults

import kr.goldenmine.impl.CalculateException
import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.impl.CompareException

class ObjectBoolean(value: Boolean) : ObjectNumber() {
    override fun clone(): KoreanObject {
        return ObjectBoolean(value)
    }

    var value = value

    constructor() : this(false)

    override fun toString(): String = value.toString()// + " / " + super.toString()

    override fun setRoot(value: Any) {
        this.value = (value as ObjectBoolean).value
    }

    override fun getRoot(): Any = value

    override fun getValue(key: String): KoreanObject? {
        when (key) {
            "부정" -> return ObjectBoolean(!value)
        }
        return null
    }

    override fun add(other: KoreanObject): KoreanObject {
        if (other is ObjectString) {
            return ObjectString(StringBuilder(value.toString()).append(other.value).toString())
        }
        throw CalculateException("이진수와 더할 수 없는 값입니다.")
    }

    override fun mul(other: KoreanObject): KoreanObject {
        return and(other)
    }

    override fun and(other: KoreanObject): KoreanObject {
        if (other is ObjectBoolean) {
            return ObjectBoolean(value and other.value)
        } else {
            throw CalculateException("이진수와 비교할 수 없는 값입니다.")
        }
    }

    override fun or(other: KoreanObject): KoreanObject {
        if (other is ObjectBoolean) {
            return ObjectBoolean(value or other.value)
        } else {
            throw CalculateException("이진수와 비교할 수 없는 값입니다.")
        }
    }

    override fun xor(other: KoreanObject): KoreanObject {
        if (other is ObjectBoolean) {
            return ObjectBoolean(value xor other.value)
        } else {
            throw CalculateException("이진수와 비교할 수 없는 값입니다.")
        }
    }

    override fun compareTo(other: KoreanObject): Int {
        if (other is ObjectBoolean) {
            return value.compareTo(other.value)
        }
        throw CompareException("비교하려는 값이 \"실수\"와 비교할 수 있는 값이 아닙니다.")
    }

}