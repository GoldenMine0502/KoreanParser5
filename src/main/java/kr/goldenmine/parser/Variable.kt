package kr.goldenmine.parser

import kr.goldenmine.impl.CalculateException
import kr.goldenmine.impl.ConstantException

class Variable : Cloneable {

    private var booleanValue: Boolean = false
    private var intValue: Long = 0
    private var realNumValue: Double = 0.toDouble()
    private var stringValue: String? = null
    private var objectValue: Comparable<Any>? = null

    private var isConst: Boolean = false

    var mode: VariableMode
        private set


    val isTrue: Boolean
        get() {
            return when (mode) {
                VariableMode.NULL -> false
                VariableMode.BOOLEAN_MODE -> booleanValue
                VariableMode.INT_MODE -> intValue == 1L
                VariableMode.REALNUM_MODE -> realNumValue == 1.0
                VariableMode.STRING_MODE -> false
                VariableMode.OBJECT_MODE -> false
            }
        }


    enum class VariableMode private constructor(mode: Int) {
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

    constructor(variable: Variable) {
        this.mode = variable.mode
        this.booleanValue = variable.booleanValue
        this.intValue = variable.intValue
        this.realNumValue = variable.realNumValue
        this.objectValue = variable.objectValue
        this.stringValue = variable.stringValue
        this.isConst = variable.isConst
    }

    constructor(mode: VariableMode, isConst: Boolean) {
        this.mode = mode
        this.isConst = isConst
    }

    //    public Variable(VariableMode mode, Object data) {
    //        this.mode = mode;
    //        set(data);
    //    }

    constructor(value: Long, isConst: Boolean) {
        intValue = value
        mode = VariableMode.INT_MODE

        this.isConst = isConst
    }

    constructor(value: Double, isConst: Boolean) {
        realNumValue = value
        mode = VariableMode.REALNUM_MODE

        this.isConst = isConst
    }

    constructor(value: Boolean, isConst: Boolean) {
        booleanValue = value
        mode = VariableMode.BOOLEAN_MODE

        this.isConst = isConst
    }

    constructor(value: String, isConst: Boolean) {
        stringValue = value
        mode = VariableMode.STRING_MODE

        this.isConst = isConst
    }

    fun setConst() {
        isConst = true
    }

    fun get(): Any? {
        when (mode) {
            VariableMode.BOOLEAN_MODE -> return booleanValue()
            VariableMode.INT_MODE -> return intValue()
            VariableMode.REALNUM_MODE -> return realNumValue()
            VariableMode.STRING_MODE -> return stringValue()
            VariableMode.OBJECT_MODE -> return objectValue()
        }
        return null
    }

    fun toBoolean(): Boolean {
        when (mode) {
            VariableMode.BOOLEAN_MODE -> return booleanValue()
            VariableMode.INT_MODE -> return intValue() != 0L
            VariableMode.REALNUM_MODE -> return realNumValue() != 0.0
            VariableMode.STRING_MODE -> throw CalculateException("String을 boolean으로 바꿀 수 없습니다. 0001")
            VariableMode.OBJECT_MODE -> throw CalculateException("Object를 boolean으로 바꿀 수 없습니다.")
        }
        throw CalculateException("boolean으로 바꿀 수 없습니다. 0000")
    }

    fun OR(variable: Variable): Boolean {
        when (mode) {
            VariableMode.BOOLEAN_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return booleanValue() || variable.booleanValue()
                VariableMode.INT_MODE -> return booleanValue() || variable.intValue() != 0L
                VariableMode.REALNUM_MODE -> return booleanValue() || variable.realNumValue() != 0.0
                VariableMode.STRING_MODE -> throw CalculateException("OR에서 String 연산은 불가합니다.")
                VariableMode.OBJECT_MODE -> throw CalculateException("OR에서 Object 연산은 불가합니다.")
            }
            VariableMode.INT_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return intValue() != 0L || variable.booleanValue()
                VariableMode.INT_MODE -> return intValue() != 0L || variable.intValue() != 0L
                VariableMode.REALNUM_MODE -> return intValue() != 0L || variable.realNumValue() != 0.0
                VariableMode.STRING_MODE -> throw CalculateException("OR에서 String 연산은 불가합니다.")
                VariableMode.OBJECT_MODE -> throw CalculateException("OR에서 Object 연산은 불가합니다.")
            }
            VariableMode.REALNUM_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return realNumValue() != 0.0 || variable.booleanValue()
                VariableMode.INT_MODE -> return realNumValue() != 0.0 || variable.intValue() != 0L
                VariableMode.REALNUM_MODE -> return realNumValue() != 0.0 || variable.realNumValue() != 0.0
                VariableMode.STRING_MODE -> throw CalculateException("OR에서 String 연산은 불가합니다.")
                VariableMode.OBJECT_MODE -> throw CalculateException("OR에서 Object 연산은 불가합니다.")
            }
            VariableMode.STRING_MODE -> throw CalculateException("OR에서 String 연산은 불가합니다.")
            VariableMode.OBJECT_MODE -> throw CalculateException("OR에서 Object 연산은 불가합니다.")
        }
        throw CalculateException("알 수 없는 이유로 OR연산을 진행할 수 없습니다.")
    }

    fun AND(variable: Variable): Boolean {
        when (mode) {
            VariableMode.BOOLEAN_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return booleanValue() && variable.booleanValue()
                VariableMode.INT_MODE -> return booleanValue() && variable.intValue() != 0L
                VariableMode.REALNUM_MODE -> return booleanValue() && variable.realNumValue() != 0.0
                VariableMode.STRING_MODE -> throw CalculateException("AND에서 String 연산은 불가합니다. 0002")
                VariableMode.OBJECT_MODE -> throw CalculateException("AND에서 Object 연산은 불가합니다.")
            }
            VariableMode.INT_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return intValue() != 0L && variable.booleanValue()
                VariableMode.INT_MODE -> return intValue() != 0L && variable.intValue() != 0L
                VariableMode.REALNUM_MODE -> return intValue() != 0L && variable.realNumValue() != 0.0
                VariableMode.STRING_MODE -> throw CalculateException("AND에서 String 연산은 불가합니다. 0002")
                VariableMode.OBJECT_MODE -> throw CalculateException("AND에서 Object 연산은 불가합니다.")
            }
            VariableMode.REALNUM_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return realNumValue() != 0.0 || variable.booleanValue()
                VariableMode.INT_MODE -> return realNumValue() != 0.0 || variable.realNumValue() != 0.0
                VariableMode.REALNUM_MODE -> return realNumValue() != 0.0 || variable.realNumValue() != 0.0
                VariableMode.STRING_MODE -> throw CalculateException("AND에서 String 연산은 불가합니다. 0002")
                VariableMode.OBJECT_MODE -> throw CalculateException("AND에서 Object 연산은 불가합니다.")
            }
            VariableMode.STRING_MODE -> throw CalculateException("AND에서 String 연산은 불가합니다. 0001")
            VariableMode.OBJECT_MODE -> throw CalculateException("AND에서 Object 연산은 불가합니다.")
        }
        throw CalculateException("알 수 없는 이유로 AND연산을 진행할 수 없습니다. 0000")
    }



    override fun equals(variable: Any?): Boolean {
        if(variable is Variable) {
            //println(mode)
            //println(variable.mode)

            when (mode) {
                VariableMode.BOOLEAN_MODE -> when (variable.mode) {
                    VariableMode.BOOLEAN_MODE -> return booleanValue() == variable.booleanValue()
                    VariableMode.INT_MODE -> return booleanValue() == (variable.intValue() != 0L)
                    VariableMode.REALNUM_MODE -> return booleanValue() == (variable.realNumValue() != 0.0)
                    VariableMode.STRING_MODE -> return false
                    VariableMode.OBJECT_MODE -> return false
                }
                VariableMode.INT_MODE -> {
                    when (variable.mode) {
                        VariableMode.BOOLEAN_MODE -> return intValue() != 0L == variable.booleanValue()
                        VariableMode.INT_MODE -> {
                            //println("vv: " + intValue())
                            //println("vv: " + variable.intValue())
                            return intValue() == variable.intValue()
                        }
                        VariableMode.REALNUM_MODE -> return intValue().toDouble() == variable.realNumValue()
                        VariableMode.STRING_MODE -> return false
                        VariableMode.OBJECT_MODE -> return false
                    }
                }

                VariableMode.REALNUM_MODE -> when (variable.mode) {
                    VariableMode.BOOLEAN_MODE -> return realNumValue() != 0.0 == variable.booleanValue()
                    VariableMode.INT_MODE -> return realNumValue() == variable.intValue().toDouble()
                    VariableMode.REALNUM_MODE -> return realNumValue() == variable.realNumValue()
                    VariableMode.STRING_MODE -> return false
                    VariableMode.OBJECT_MODE -> return false
                }
                VariableMode.STRING_MODE -> when (variable.mode) {

                    VariableMode.BOOLEAN_MODE -> return false
                    VariableMode.INT_MODE -> return false
                    VariableMode.REALNUM_MODE -> return false
                    VariableMode.STRING_MODE -> return stringValue() == variable.stringValue()
                    VariableMode.OBJECT_MODE -> return false
                }

                VariableMode.OBJECT_MODE -> when (variable.mode) {

                    VariableMode.BOOLEAN_MODE -> return false
                    VariableMode.INT_MODE -> return false
                    VariableMode.REALNUM_MODE -> return false
                    VariableMode.STRING_MODE -> return false
                    VariableMode.OBJECT_MODE -> return variable.objectValue() == objectValue()
                }
            }
        }
        //println("not compared")
        return false
    }

    @Throws(CloneNotSupportedException::class)
    override fun clone(): Variable {

        return super.clone() as Variable
    }

    fun smallerThan(variable: Variable): Boolean {
        when (mode) {
            VariableMode.BOOLEAN_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return !booleanValue() && variable.booleanValue()
                VariableMode.INT_MODE -> return if (booleanValue()) {
                    variable.intValue() > 1
                } else {
                    variable.intValue() > 0
                }
                VariableMode.REALNUM_MODE -> return if (booleanValue()) {
                    variable.realNumValue() > 1
                } else {
                    variable.realNumValue() > 0
                }
            }
            VariableMode.INT_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return if (variable.booleanValue()) {
                    intValue() < 1
                } else {
                    intValue() < 0
                }
                VariableMode.INT_MODE -> return intValue() < variable.intValue()
                VariableMode.REALNUM_MODE -> return intValue() < variable.realNumValue()
            }
            VariableMode.REALNUM_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return if (variable.booleanValue()) {
                    realNumValue() < 1
                } else {
                    realNumValue() < 0
                }
                VariableMode.INT_MODE -> return realNumValue() < variable.intValue()
                VariableMode.REALNUM_MODE -> return realNumValue() < variable.realNumValue()
            }
            VariableMode.STRING_MODE -> when (variable.mode) {
                VariableMode.STRING_MODE -> return stringValue()!! > variable.stringValue()!!
            }
            VariableMode.OBJECT_MODE -> when (variable.mode) {
                VariableMode.OBJECT_MODE -> return objectValue!!.compareTo(variable.objectValue!!) > 0
            }
        }

        return false
    }


    fun biggerThan(variable: Variable): Boolean {
        when (mode) {
            VariableMode.BOOLEAN_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return booleanValue() && !variable.booleanValue()
                VariableMode.INT_MODE -> return if (booleanValue()) {
                    variable.intValue() < 1
                } else {
                    variable.intValue() < 0
                }
                VariableMode.REALNUM_MODE -> return if (booleanValue()) {
                    variable.realNumValue() < 1
                } else {
                    variable.realNumValue() < 0
                }
                VariableMode.STRING_MODE -> return false
            }
            VariableMode.INT_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return if (variable.booleanValue()) {
                    intValue() > 1
                } else {
                    intValue() > 0
                }
                VariableMode.INT_MODE -> return intValue() > variable.intValue()
                VariableMode.REALNUM_MODE -> return intValue() > variable.realNumValue()
            }
            VariableMode.REALNUM_MODE -> when (variable.mode) {
                VariableMode.BOOLEAN_MODE -> return if (variable.booleanValue()) {
                    realNumValue() > 1
                } else {
                    realNumValue() > 0
                }
                VariableMode.INT_MODE -> return realNumValue() > variable.intValue()
                VariableMode.REALNUM_MODE -> return realNumValue() > variable.realNumValue()
            }
            VariableMode.STRING_MODE -> when (variable.mode) {
                VariableMode.STRING_MODE -> return stringValue()!!.compareTo(variable.stringValue()!!) < 0
            }
            VariableMode.OBJECT_MODE -> when (variable.mode) {
                VariableMode.OBJECT_MODE -> return objectValue!!.compareTo(variable.objectValue!!) < 0
            }
        }

        return false
    }

    fun set(obj: Any?) {
        if (!isConst) {
            when (mode) {
                VariableMode.BOOLEAN_MODE -> booleanValue = obj as Boolean
                VariableMode.INT_MODE -> intValue = obj as Long
                VariableMode.REALNUM_MODE -> realNumValue = obj as Double
                VariableMode.STRING_MODE -> stringValue = obj as String
                VariableMode.OBJECT_MODE -> objectValue = obj as Comparable<Any>
            }
        } else {
            throw ConstantException("상수 변수입니다.")
        }
    }

    fun castCompelNoMaintain(mode: VariableMode) {
        this.mode = mode
        if (mode != VariableMode.STRING_MODE) {
            stringValue = null
        }
        if (mode != VariableMode.OBJECT_MODE) {
            objectValue = null
        }
    }

    fun castCompel(toMode: VariableMode) {
        if (mode!!.mode != toMode.mode) {
            when (mode) {
                VariableMode.INT_MODE -> when (toMode) {
                    VariableMode.BOOLEAN_MODE -> booleanValue = intValue and 0x1 == 1L
                    VariableMode.REALNUM_MODE -> realNumValue = intValue.toDouble()
                    VariableMode.STRING_MODE -> stringValue = intValue.toString()
                }
                VariableMode.BOOLEAN_MODE -> when (toMode) {
                    VariableMode.INT_MODE -> intValue = (if (booleanValue) 1 else 0).toLong()
                    VariableMode.REALNUM_MODE -> realNumValue = (if (booleanValue) 1 else 0).toDouble()
                    VariableMode.STRING_MODE -> stringValue = booleanValue.toString()
                }
                VariableMode.REALNUM_MODE -> when (toMode) {
                    VariableMode.BOOLEAN_MODE -> booleanValue = realNumValue.toInt() and 0x1 == 1
                    VariableMode.INT_MODE -> intValue = realNumValue.toLong()
                    VariableMode.STRING_MODE -> stringValue = realNumValue.toString()
                }
                VariableMode.STRING_MODE -> when (toMode) {
                    VariableMode.BOOLEAN_MODE -> booleanValue = java.lang.Boolean.parseBoolean(stringValue)
                    VariableMode.INT_MODE -> intValue = java.lang.Long.parseLong(stringValue!!)
                    VariableMode.REALNUM_MODE -> realNumValue = java.lang.Double.parseDouble(stringValue!!)
                }
            }

            mode = toMode
        }
    }

    fun cast(toMode: VariableMode) {
        if (mode!!.mode < toMode.mode) {
            castCompel(toMode)
        }
    }

    fun intValue(): Long {
        return intValue
    }

    fun realNumValue(): Double {
        return realNumValue
    }

    fun stringValue(): String? {
        return stringValue
    }

    fun booleanValue(): Boolean {
        return booleanValue
    }

    fun objectValue(): Any? {
        return objectValue
    }

    //    public Object value() {
    //        switch(mode) {
    //            case INT_MODE:
    //                return intValue();
    //            case REALNUM_MODE:
    //                return realNumValue();
    //            case STRING_MODE:
    //                return stringValue();
    //            case BOOLEAN_MODE:
    //                return booleanValue();
    //        }
    //
    //        return null;
    //    }

    override fun toString(): String {
        return mode.toString() + ", " + get() + " / " + super.toString()
    }
    //
    //    public static Variable initVariable(KoreanProgramVariableStorage variableStorage, Element value, Element valueType) {
    //        int valueTypeInt = (int) valueType.getRootLong();
    //        //System.out.println(value + ", " + valueTypeInt + ", " + valueType);
    //
    //        if(valueTypeInt!= VariableVerify.IS_VARIABLE) {
    //
    //            Variable tempVariable = null;
    //
    //            switch(VariableMode.get(valueTypeInt)) {
    //                case BOOLEAN_MODE:
    //                    tempVariable = new Variable(VariableMode.BOOLEAN_MODE, value.getRootBoolean());
    //                    break;
    //                case INT_MODE:
    //                    tempVariable = new Variable(VariableMode.INT_MODE, value.getRootLong());
    //                    break;
    //                case REALNUM_MODE:
    //                    tempVariable = new Variable(VariableMode.REALNUM_MODE, value.getRootDouble());
    //                    break;
    //                case STRING_MODE:
    //                    tempVariable = new Variable(VariableMode.STRING_MODE, value.getRoot());
    //                    break;
    //            }
    //
    //            return tempVariable;
    //        } else {
    //            Variable variable = variableStorage.getVariables(value.getRoot());
    //
    //            return variable;
    //        }
    //    }
    //
    //
    //    public static String processVariable(KoreanProgramVariableStorage storage, String source) {
    //        StringBuilder sb = new StringBuilder(source);
    //        processVariable(sb, 0, false, storage);
    //
    //        return sb.toString();
    //    }
    //
    //    public static int processVariable(StringBuilder sb, int pos, boolean inner, KoreanProgramVariableStorage storage) {
    //        int start = pos;
    //
    //        while (pos < sb.length()) {
    //            char ch = sb.charAt(pos);
    //            ++pos;
    //            if (ch == '<')
    //                pos = processVariable(sb, pos, true, storage);
    //            else if (ch == '>')
    //                break;
    //        }
    //
    //        if (inner) {
    //            try {
    //                String key = sb.substring(start, pos - 1);
    //                String val = storage.getVariables(key).valueToStr();
    //
    //                if (val != null) {
    //                    sb.delete(start - 1, pos);
    //                    sb.insert(start - 1, val);
    //
    //                    pos = start - 1 + val.length();
    //                }
    //            } catch(Exception ex) {
    //                //System.out.println(sb.substring(start, pos - 1));
    //                //System.out.println(storage.getVariables(sb.substring(start, pos - 1)).get());
    //            }
    //        }
    //        // System.out.println(sb.toString());
    //        return pos;
    //    }

    //open operator fun equals(other: Any?): Boolean = equals(other)
}
