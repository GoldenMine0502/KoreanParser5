package kr.goldenmine.objects.objects.arrays

import kr.goldenmine.impl.CompareException
import kr.goldenmine.impl.ObjectException
import kr.goldenmine.objects.KoreanObject
import kr.goldenmine.objects.objects.defaults.ObjectInteger

class ObjectIntArray : ObjectArray() {
    override fun clone(): KoreanObject {
        val x = ObjectIntArray()
        x.array = arrayOfNulls(array!!.size)
        for(i in array!!.indices) {
            x.array!![i] = array!![i]!!.clone() as ObjectInteger
        }

        return x
    }

    var array: Array<ObjectInteger?>? = null

    override fun getArray(): Array<out KoreanObject?>? = array

    override fun createAndSetArray(size: Int): Array<out KoreanObject?>? = arrayOfNulls<ObjectInteger?>(size).apply { array = this }
    override fun getValue(key: String): KoreanObject? {
        val x = super.getValue(key)
        if(x != null) return x

        when(key) {
            "합", "합계" -> {
                var sum = 0L
                array!!.forEach {
                    sum += it!!.value
                }

                return ObjectInteger(sum)
            }
        }

        return null
    }

    override fun setArrayIndex(index: Int, obj: KoreanObject?) = array!!.set(index, (obj as ObjectInteger?))
}
