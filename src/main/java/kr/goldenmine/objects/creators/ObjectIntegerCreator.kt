package kr.goldenmine.objects.creators

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.objects.KoreanObjectCreator
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.defaults.ObjectInteger

class ObjectIntegerCreator : KoreanObjectCreator {
    override val type: String
        get() = "정수"
    override val regex: String
        get() = "[c]"
    override val instance: KoreanObject
        get() = ObjectInteger()

    override fun getValueType(key: String): VariableMode {
        return VariableMode.NULL
    }

}