package kr.goldenmine.objects.creators

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.objects.KoreanObjectCreator
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.defaults.ObjectBoolean

class ObjectBooleanCreator : KoreanObjectCreator {
    override val type: String
        get() = "논리"
    override val regex: String
        get() = "[b]"
    override val instance: KoreanObject
        get() = ObjectBoolean()

    override fun getValueType(key: String): VariableMode {
        return VariableMode.NULL
    }

}