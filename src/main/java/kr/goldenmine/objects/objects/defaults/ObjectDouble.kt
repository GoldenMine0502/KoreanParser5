package kr.goldenmine.objects.objects.defaults

import kr.goldenmine.impl.CalculateException
import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.impl.CompareException

class ObjectDouble(value: Double) : ObjectNumber() {
    override fun clone(): KoreanObject {
        return ObjectDouble(value)
    }

    var value = value

    constructor() : this(0.0)

    override fun toString(): String = value.toString()// + " / " + super.toString()

    override fun setRoot(value: Any) {
        if(value is ObjectDouble) {
            this.value = value.value
        }
        if(value is ObjectInteger) {
            this.value = value.value.toDouble()
        }
        if(value is ObjectChar) {
            this.value = value.value.toDouble()
        }
        if(value is Double) {
            this.value = value
        }
        if(value is Long) {
            this.value = value.toDouble()
        }
        if(value is Char) {
            this.value = value.toDouble()
        }
    }

    override fun add(other: KoreanObject): KoreanObject {
        if(other is ObjectChar) {
            return ObjectDouble((value + other.value.toInt()))
        }
        if(other is ObjectInteger) {
            return ObjectDouble((value + other.value))
        }
        if(other is ObjectDouble) {
            return ObjectDouble((value + other.value))
        }
        if (other is ObjectString) {
            return ObjectString(StringBuilder(value.toString()).append(other.str).toString())
        }
        throw CalculateException("실수와 더할 수 없는 값입니다.")
    }

    override fun sub(other: KoreanObject): KoreanObject {
        if(other is ObjectChar) {
            return ObjectDouble((value - other.value.toInt()))
        }
        if(other is ObjectInteger) {
            return ObjectDouble((value - other.value))
        }
        if(other is ObjectDouble) {
            return ObjectDouble((value - other.value))
        }
        throw CalculateException("실수와 뺄 수 없는 값입니다.")
    }

    override fun mul(other: KoreanObject): KoreanObject {
        if(other is ObjectChar) {
            return ObjectDouble((value * other.value.toInt()))
        }
        if(other is ObjectInteger) {
            return ObjectDouble((value * other.value))
        }
        if(other is ObjectDouble) {
            return ObjectDouble((value * other.value))
        }
        throw CalculateException("실수와 곱할 수 없는 값입니다.")
    }

    override fun div(other: KoreanObject): KoreanObject {
        if(other is ObjectChar) {
            return ObjectDouble(value / other.value.toInt())
        }
        if(other is ObjectInteger) {
            return ObjectDouble(value / other.value)
        }
        if(other is ObjectDouble) {
            return ObjectDouble(value / other.value)
        }
        throw CalculateException("실수와 나눌 수 없는 값입니다.")
    }

    override fun mod(other: KoreanObject): KoreanObject {
        if(other is ObjectChar) {
            return ObjectDouble((value % other.value.toInt()))
        }
        if(other is ObjectInteger) {
            return ObjectDouble(value % other.value)
        }
        if(other is ObjectDouble) {
            return ObjectDouble(value % other.value)
        }
        throw CalculateException("실수와 나눌 수 없는 값입니다.")
    }

    override fun getRoot(): Any = value

    override fun getValue(key: String): KoreanObject? {

        return null
    }


    override fun compareTo(other: KoreanObject): Int {
        if(other is ObjectDouble) {
            return value.compareTo(other.value)
        }
        if(other is ObjectInteger) {
            return value.compareTo(other.value)
        }

        throw CompareException("비교하려는 값이 \"실수\"와 비교할 수 있는 값이 아닙니다.")
    }

}