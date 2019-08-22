package kr.goldenmine.`object`

import kr.goldenmine.parser.Variable

class Point2DCreator : KoreanObjectCreator {
    override val type: String
        get() = "xy좌표"

    override val regex: String
        get() = "[d], [d]"

    override val instance: KoreanObject
        get() = Point2D()

    override fun getValueType(key: String): Variable.VariableMode {
        when (key) {
            "x", "X", "x좌표", "X좌표" -> return Variable.VariableMode.REALNUM_MODE
            "y", "Y", "y좌표", "Y좌표" -> return Variable.VariableMode.REALNUM_MODE
        }

        return Variable.VariableMode.NULL
    }
}
