package kr.goldenmine.objects.creators

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.objects.KoreanObjectCreator
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.arrays.ObjectIntArray

// 크기 10으로 정수배열을 만듭니다

class ObjectIntArrayCreator : KoreanObjectCreator {
    override val type: String
        get() = "정수배열"
    override val regex: String
        get() = "[c]"
    override val instance: KoreanObject
        get() = ObjectIntArray()

    override fun getValueType(key: String): VariableMode {
        return VariableMode.NULL
    }

}