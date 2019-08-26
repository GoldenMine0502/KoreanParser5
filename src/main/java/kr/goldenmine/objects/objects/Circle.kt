package kr.goldenmine.objects.objects

import kr.goldenmine.impl.CompareException
import kr.goldenmine.impl.ObjectException
import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.objects.objects.defaults.ObjectDouble

open class Circle(var point2D: Point2D, radius: Double) : KoreanObject {
    override fun toString(): String = "$point2D, 반지름: $radius"

    var radius = ObjectDouble(radius)

    constructor() : this(Point2D(), 1.0)

    override fun getRoot(): Any {
        throw ObjectException("객체에는 root가 존재하지 않습니다.")
    }

    override fun setRoot(value: Any) {
        throw ObjectException("객체에는 root가 존재하지 않습니다.")
    }

    override fun getValue(key: String): KoreanObject {
        when(key) {
            "좌표" -> return point2D
            "반지름" -> return radius
        }

        throw ObjectException("해당 변수는 $key 속성을 갖고있지 않습니다.")
    }

    override fun setValue(key: String, value: KoreanObject) {
        when(key) {
            "좌표" -> point2D = value as Point2D
            "반지름" -> radius = value as ObjectDouble
        }
    }

    override fun setValue(index: Int, value: KoreanObject) {
        when(index) {
            0 -> point2D = value as Point2D // swallow
            1 -> radius.setRoot(value) // deep
        }
    }

    override fun compareTo(other: KoreanObject): Int {
        if(other is Circle) {
            when(point2D.compareTo(other.point2D)) {
                0 -> {
                    return radius.compareTo(other.radius)
                }
                1 -> return 1
                -1 -> return -1
            }
        }
        throw CompareException("비교하려는 값이 \"원\"과 비교할 수 있는 값이 아닙니다.")
    }

}