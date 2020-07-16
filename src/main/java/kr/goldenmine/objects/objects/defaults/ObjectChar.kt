package kr.goldenmine.objects.objects.defaults
//
//import kr.goldenmine.impl.CalculateException
//import kr.goldenmine.objects.KoreanObject
//import kr.goldenmine.impl.CompareException
//
//class ObjectChar(value: Char) : ObjectNumber() {
//    override fun clone(): KoreanObject {
//        return ObjectChar(value)
//    }
//
//    var value = value
//
//    constructor() : this(0.toChar())
//
//    override fun toString(): String = value.toString()// + " / " + super.toString()
//
//    override fun setRoot(value: Any) {
//        this.value = (value as ObjectChar).value
//    }
//
//    override fun getRoot(): Any = value
//
//    override fun getValue(key: String): KoreanObject? {
//
//        return null
//    }
//
//    override fun add(other: KoreanObject): KoreanObject {
//        if(other is ObjectChar) {
//            return ObjectChar((value.toInt() + other.value.toInt()).toChar())
//        }
//        if(other is ObjectInteger) {
//            return ObjectInteger((value.toInt() + other.value))
//        }
//        if(other is ObjectDouble) {
//            return ObjectDouble((value.toDouble() + other.value))
//        }
//        if (other is ObjectString) {
//            return ObjectString(StringBuilder(value.toString()).append(other.str).toString())
//        }
//        throw CalculateException("문자와 더할 수 없는 값입니다.")
//    }
//
//    override fun sub(other: KoreanObject): KoreanObject {
//        if(other is ObjectChar) {
//            return ObjectChar((value.toInt() - other.value.toInt()).toChar())
//        }
//        if(other is ObjectInteger) {
//            return ObjectInteger((value.toInt() - other.value))
//        }
//        if(other is ObjectDouble) {
//            return ObjectDouble((value.toDouble() - other.value))
//        }
//        throw CalculateException("문자와 뺄 수 없는 값입니다.")
//    }
//
//    override fun mul(other: KoreanObject): KoreanObject {
//        if(other is ObjectChar) {
//            return ObjectChar((value.toInt() * other.value.toInt()).toChar())
//        }
//        if(other is ObjectInteger) {
//            return ObjectInteger((value.toInt() * other.value))
//        }
//        if(other is ObjectDouble) {
//            return ObjectDouble((value.toDouble() * other.value))
//        }
//        throw CalculateException("문자와 곱할 수 없는 값입니다.")
//}
//
//    override fun div(other: KoreanObject): KoreanObject {
//        if(other is ObjectChar) {
//            val first = value.toLong()
//            val second = other.value.toLong()
//            return if(first % second == 0L) { ObjectChar((first / second).toChar()) } else { ObjectDouble((first.toDouble() / second.toDouble())) }
//        }
//        if(other is ObjectInteger) {
//            val first = value.toLong()
//            val second = other.value
//            return if(first % second == 0L) { ObjectInteger((first / second)) } else { ObjectDouble((first.toDouble() / second.toDouble())) }
//        }
//        if(other is ObjectDouble) {
//            return ObjectDouble(value.toDouble() / other.value)
//        }
//        throw CalculateException("문자와 나눌 수 없는 값입니다.")
//    }
//
//    override fun mod(other: KoreanObject): KoreanObject {
//        if(other is ObjectChar) {
//            return ObjectChar((value.toLong() % other.value.toLong()).toChar())
//        }
//        if(other is ObjectInteger) {
//            return ObjectInteger(value.toLong() % other.value)
//        }
//        if(other is ObjectDouble) {
//            return ObjectDouble(value.toDouble() % other.value)
//        }
//        return super.mod(other)
//    }
//
//    override fun compareTo(other: KoreanObject): Int {
//        if(other is ObjectChar) {
//            return value.compareTo(other.value)
//        }
//
//        throw CompareException("비교하려는 값이 \"문자\"와 비교할 수 있는 값이 아닙니다.")
//    }
//
//}