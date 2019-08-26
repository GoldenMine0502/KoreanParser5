package kr.goldenmine.objects.creators

import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.objects.KoreanObjectCreator
import kr.goldenmine.objects.VariableMode
import kr.goldenmine.objects.objects.Point2D
import kr.goldenmine.objects.objects.Point3D

class Point3DCreator : KoreanObjectCreator {
    override val type: String
        get() = "xyz좌표"

    override val regex: String
        get() = "[d], [d], [d]"

    override val instance: KoreanObject
        get() = Point3D()

    override fun getValueType(key: String): VariableMode {
        when (key) {
            "x", "X", "x좌표", "X좌표" -> return VariableMode.REALNUM_MODE
            "y", "Y", "y좌표", "Y좌표" -> return VariableMode.REALNUM_MODE
            "z", "Z", "z좌표", "Z좌표" -> return VariableMode.REALNUM_MODE
        }

        return VariableMode.NULL
    }
}
