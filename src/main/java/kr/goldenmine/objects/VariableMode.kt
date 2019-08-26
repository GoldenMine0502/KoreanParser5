package kr.goldenmine.objects

enum class VariableMode constructor(mode: Int) {
    NULL(-1),
    BOOLEAN_MODE(0),
    INT_MODE(1),
    REALNUM_MODE(2),
    STRING_MODE(3),
    //VARIABLE_MODE(4),
    OBJECT_MODE(99);


    var mode: Int = 0
        internal set

    init {
        this.mode = mode
    }

    companion object {

        operator fun get(mode: Int): VariableMode? {
            when (mode) {
                0 -> return BOOLEAN_MODE
                1 -> return INT_MODE
                2 -> return REALNUM_MODE
                3 -> return STRING_MODE
                4 -> return OBJECT_MODE
            }

            return null
        }
    }
}