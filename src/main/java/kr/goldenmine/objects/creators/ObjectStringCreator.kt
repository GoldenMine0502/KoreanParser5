package kr.goldenmine.objects.creators

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.objects.KoreanObjectCreator
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.defaults.ObjectString

class ObjectStringCreator : KoreanObjectCreator {
    override val type: String
        get() = "문자열"
    override val regex: String
        get() = "[s]"
    override val instance: KoreanObject
        get() = ObjectString()

    override fun getValueType(key: String): VariableMode {
        when(key) {
            "자리수", "자릿수" -> VariableMode.INT_MODE
        }
        return VariableMode.NULL
    }

}