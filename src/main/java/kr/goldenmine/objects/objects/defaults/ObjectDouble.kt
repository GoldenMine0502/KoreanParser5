package kr.goldenmine.objects.objects.defaults

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.impl.CompareException

class ObjectDouble(value: Double) : ObjectNumber() {
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