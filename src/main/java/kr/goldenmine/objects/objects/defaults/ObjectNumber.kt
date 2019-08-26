package kr.goldenmine.objects.objects.defaults

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.impl.ObjectException

abstract class ObjectNumber : KoreanObject {

    override fun toString(): String {
        return super.toString()
    }

    override fun setValue(key: String, value: KoreanObject) {
        throw ObjectException("기본형 변수는 추가적인 필드를 갖지 못합니다.")
    }

    override fun setValue(index: Int, value: KoreanObject) {
        throw ObjectException("기본형 변수는 추가적인 필드를 갖지 못합니다.")
    }
}