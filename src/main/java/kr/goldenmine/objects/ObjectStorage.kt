package kr.goldenmine.objects

import kr.goldenmine.objects.creators.*
import java.util.*

class ObjectStorage private constructor() {

    val creators = HashMap<String, KoreanObjectCreator>()

    init {
        init()
    }

    private fun init() {
        addCreator(Point2DCreator())
        addCreator(Point3DCreator())
        addCreator(ObjectBooleanCreator())
//        addCreator(ObjectCharCreator())
        addCreator(ObjectIntegerCreator())
        addCreator(ObjectStringCreator())
    }

    fun addCreator(creator: KoreanObjectCreator) {
        creators[creator.type] = creator
    }

    fun getCreator(str: String) : KoreanObjectCreator? {
        return creators[str]
    }

    companion object {
        val INSTANCE = ObjectStorage()
    }
}
