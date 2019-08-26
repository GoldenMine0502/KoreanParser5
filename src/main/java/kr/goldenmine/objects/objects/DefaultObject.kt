package kr.goldenmine.objects.objects

import kr.goldenmine.impl.CompareException
import kr.goldenmine.impl.ObjectException
import kr.goldenmine.objects.KoreanObject

class DefaultObject : KoreanObject {
    override fun toString(): String = ""
    override fun getRoot(): Any {
        throw ObjectException("객체에는 root가 존재하지 않습니다.")
    }

    override fun setRoot(value: Any) {
        throw ObjectException("객체에는 root가 존재하지 않습니다.")
    }

    override fun getValue(key: String): KoreanObject {
        throw ObjectException("해당 변수는 $key 속성을 갖고있지 않습니다.")
    }

    override fun setValue(key: String, value: KoreanObject) {

    }

    override fun setValue(index: Int, value: KoreanObject) {

    }

    override fun compareTo(other: KoreanObject): Int {
        throw CompareException("비교하려는 값이 \"\"와 비교할 수 있는 값이 아닙니다.")
    }

}