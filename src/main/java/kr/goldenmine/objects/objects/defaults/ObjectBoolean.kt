package kr.goldenmine.objects.objects.defaults

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.impl.CompareException

class ObjectBoolean(value: Boolean) : ObjectNumber() {
    var value = value

    constructor() : this(false)

    override fun toString(): String = value.toString()// + " / " + super.toString()

    override fun setRoot(value: Any) {
        this.value = (value as ObjectBoolean).value
    }

    override fun getRoot(): Any = value

    override fun getValue(key: String): KoreanObject? {
        when(key) {
            "부정" -> return ObjectBoolean(!value)
        }
        return null
    }



    override fun compareTo(other: KoreanObject): Int {
        if(other is ObjectBoolean) {
            return value.compareTo(other.value)
        }
        throw CompareException("비교하려는 값이 \"실수\"와 비교할 수 있는 값이 아닙니다.")
    }

}