package kr.goldenmine.objects.creators

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.objects.KoreanObjectCreator
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.defaults.ObjectDouble

class ObjectDoubleCreator : KoreanObjectCreator {
    override val type: String
        get() = "실수"
    override val regex: String
        get() = "[d]"
    override val instance: KoreanObject
        get() = ObjectDouble()

    override fun getValueType(key: String): VariableMode {
        return VariableMode.NULL
    }

}