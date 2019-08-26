package kr.goldenmine.objects.objects.defaults

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.impl.CompareException

class ObjectChar(value: Char) : ObjectNumber() {
    var value = value

    constructor() : this(0.toChar())

    override fun toString(): String = value.toString()// + " / " + super.toString()

    override fun setRoot(value: Any) {
        this.value = (value as ObjectChar).value
    }

    override fun getRoot(): Any = value

    override fun getValue(key: String): KoreanObject? {

        return null
    }



    override fun compareTo(other: KoreanObject): Int {
        if(other is ObjectChar) {
            return value.compareTo(other.value)
        }

        throw CompareException("비교하려는 값이 \"문자\"와 비교할 수 있는 값이 아닙니다.")
    }

}