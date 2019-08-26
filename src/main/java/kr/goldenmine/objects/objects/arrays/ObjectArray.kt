package kr.goldenmine.objects.objects.arrays

import kr.goldenmine.impl.CompareException
import kr.goldenmine.impl.ObjectException
import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.objects.objects.defaults.ObjectInteger

abstract class ObjectArray : KoreanObject {

    abstract fun getArray(): Array<out KoreanObject?>?

    abstract fun createAndSetArray(size: Int): Array<out KoreanObject?>?

    abstract fun setArrayIndex(index: Int, obj: KoreanObject?)


    override fun getRoot(): Any {
        throw ObjectException("객체에는 root가 존재하지 않습니다.")
    }

    override fun setRoot(value: Any) {
        throw ObjectException("객체에는 root가 존재하지 않습니다.")
    }

    override fun getValue(key: String): KoreanObject? {
        val array = getArray()

        if (array != null) {
            if (key.endsWith("번 인덱스")) {
                return array[key.substring(0, key.length - 5).toInt()]
            }
            if (key.endsWith("번인덱스")) {
                return array[key.substring(0, key.length - 4).toInt()]
            }
        } else {
            throw ObjectException("초기화되지 않은 배열입니다. \"배열의 크기를 n으로 설정합니다\"을 통해 초기화하십시오.")
        }
        return null
    }

    override fun setValue(key: String, value: KoreanObject) {
        when (key) {
            "크기", "사이즈" -> {
                if (value is ObjectInteger) {
                    createAndSetArray(value.getRoot() as Int)
                } else {
                    throw ObjectException("배열의 크기는 정수로만 설정할 수 있습니다.")
                }
            }
        }
    }

    override fun setValue(index: Int, value: KoreanObject) {

    }

    override fun compareTo(other: KoreanObject): Int {
        throw CompareException("배열은 비교가 불가능합니다.")
    }

    override fun toString(): String = if(getArray() != null) { arrayToString(getArray()!!) } else "null"
}

fun arrayToString(array: Array<out Any?>): String {
    val sb = StringBuilder()
    sb.append("[")
    for(i in array.indices) {
        if(i != 0) sb.append(", ")
        sb.append(array[i])
    }
    sb.append("]")

    return sb.toString()
}