package kr.goldenmine.objects.creators

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.objects.KoreanObjectCreator
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.Point2D

class Point2DCreator : KoreanObjectCreator {
    override val type: String
        get() = "xy좌표"

    override val regex: String
        get() = "[d], [d]"

    override val instance: KoreanObject
        get() = Point2D()

    override fun getValueType(key: String): VariableMode {
        when (key) {
            "x", "X", "x좌표", "X좌표" -> return VariableMode.REALNUM_MODE
            "y", "Y", "y좌표", "Y좌표" -> return VariableMode.REALNUM_MODE
        }

        return VariableMode.NULL
    }
}
