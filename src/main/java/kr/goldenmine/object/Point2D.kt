package kr.goldenmine.`object`

class Point2D : KoreanObject {
    internal var x: Double = 0.toDouble()
    internal var y: Double = 0.toDouble()

    // a와 b로 좌표를 만듭니다.
    // new Point2D(x, y);
    // 10과 30으로 xy좌표를 만듭니다.

    override fun getValue(key: String): Any? {
        when (key) {
            "x", "X" -> return x
            "y", "Y" -> return y
        }
        return null
    }

    override fun setValue(key: String, value: Any?) {
        when (key) {
            "x", "X" -> this.x = value as Double
            "y", "Y" -> this.y = value as Double
        }
    }

    override fun setValue(index: Int, value: Any?) {
        when (index) {
            0 -> this.x = value as Double
            1 -> this.y = value as Double
        }
    }

    override fun compareTo(o: KoreanObject): Int {
        return if (o is Point2D) {

            if (x < o.x) {
                -1
            } else if (x > o.x) {
                1
            } else if (y < o.y) {
                -1
            } else if (y > o.y) {
                1
            } else {
                0
            }
        } else {
            0
        }
    }
}
