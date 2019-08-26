package kr.goldenmine.objects.objects.defaults

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.impl.CompareException
import kr.goldenmine.objects.objects.arrays.ObjectIntArray

class ObjectInteger(value: Long) : ObjectNumber() {
    constructor(value: Int) : this(value.toLong())

    constructor() : this(0)

    var value = value

    override fun toString(): String = value.toString()// + " / " + super.toString()

    override fun setRoot(value: Any) {
        if(value is ObjectChar) {
            this.value = value.value.toLong()
        }
        if(value is ObjectInteger) {
            this.value = value.value
        }
        if(value is Long) {
            this.value = value
        }
        if(value is Char) {
            this.value = value.toLong()
        }
    }

    override fun getRoot(): Any = value

    override fun getValue(key: String): KoreanObject? {
        //println(key)
        when(key) {
            "자리수", "자릿수" -> {
                var count = 0
                var valueCopied = value
                while(valueCopied >= 1) {
                    count++
                    valueCopied /= 10
                }

                //println("자리수: $count")

                return ObjectInteger(count)
            }
            "각 자리수", "각자리수", "각자릿수", "각 자릿수" -> {
                var count = 0
                var valueCopied = value
                while(valueCopied >= 1) {
                    count++
                    valueCopied /= 10
                }

                val array = ObjectIntArray()
                array.createAndSetArray(count)

                var valueCopied2 = value
                var index = 0

                while(valueCopied2 >= 1) {
                    array.setArrayIndex(index, ObjectInteger(valueCopied2 % 10))
                    index++
                    valueCopied2 /= 10
                }

                return array
            }
        }

        return null
    }

    override fun compareTo(other: KoreanObject): Int {
        if(other is ObjectDouble) {
            return value.compareTo(other.value)
        }
        if(other is ObjectInteger) {
            return value.compareTo(other.value)
        }

        throw CompareException("비교하려는 값이 \"정수\"와 비교할 수 있는 값이 아닙니다.")
    }

}