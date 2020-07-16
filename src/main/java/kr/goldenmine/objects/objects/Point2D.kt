package kr.goldenmine.objects.objects

import kr.goldenmine.impl.CompareException
import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.impl.ObjectException
import kr.goldenmine.objects.objects.defaults.ObjectDouble

/*
TODO 모든 변수는 객체로 취급한다.
TODO ~의로 끝나면 getValue()
TODO Variable클래스에 KoreanObject 하나만 놓는다
TODO VariableMode를 이용해 기본형 여부를 체크하고, 강제캐스팅하여 활용한다
 */

open class Point2D(x: Double, y: Double) : KoreanObject {
    override fun clone(): KoreanObject {
        return Point2D(x.value, y.value)
    }

    constructor() : this(0.0, 0.0)

    override fun toString(): String = "($x, $y)"

    override fun getRoot(): Any {
        throw ObjectException("객체에는 root가 존재하지 않습니다.")
    }

    override fun setRoot(value: Any) {
        throw ObjectException("객체에는 root가 존재하지 않습니다.")
    }

    internal var x: ObjectDouble = ObjectDouble(x)
    internal var y: ObjectDouble = ObjectDouble(y)

    // a와 b로 좌표를 만듭니다.
    // new Point2D(x, y);
    // 10과 30으로 xy좌표를 만듭니다.

    override fun getValue(key: String): KoreanObject? {
        when (key) {
            "x", "X" , "x좌표", "X좌표"-> return x
            "y", "Y" , "y좌표", "Y좌표"-> return y
        }
        return null
    }

    override fun setValue(key: String, value: KoreanObject) {
//        println("set: $key $value")
        when (key) {
            "x", "X" -> this.x.setRoot(value)
            "y", "Y" -> this.y.setRoot(value)
        }
    }

    override fun setValue(index: Int, value: KoreanObject) {
//        println("set: $index $value")
        when (index) {
            0 -> this.x.setRoot(value)
            1 -> this.y.setRoot(value)
        }
    }

    override fun compareTo(o: KoreanObject): Int {
        return if (o is Point2D) {
            val xComp = x.compareTo(o.x)
            when {
                xComp == 0 -> y.compareTo(o.y)
                xComp > 0 -> 1
                else -> -1
            }
        } else {
            throw CompareException("비교하려는 값이 \"xy좌표\"와 비교할 수 있는 값이 아닙니다.")
        }
    }
}
