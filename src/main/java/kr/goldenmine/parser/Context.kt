package kr.goldenmine.parser

class Context(source: String, private var isConst: Boolean, var posStart: Int, var posFinish: Int) {
    var variables: MutableList<Variable?>? = null
    var genitiveList: ArrayList<MutableList<String>?>? = null
    var source: String = source
        set(source) {
            if (!isConst) {
                field = source
            }
        }

    constructor(context: Context) : this(context.source, context.isConst, context.posStart, context.posFinish)

    fun setConst() {
        isConst = true
    }

    override fun toString(): String {
        return (if (variables != null) variables!!.toString() + ", size: " + variables!!.size else source) + ", (" + posStart + ", " + posFinish + ")"
    }

    companion object {

        fun parseVariable(str: String): Variable {
            if (str.startsWith("\"") && str.endsWith("\"") || str.startsWith("\'") && str.endsWith("\'")) {
                return Variable(str.substring(1, str.length - 1), false)
            }
            if (isBoolean(str)) {
                return Variable(java.lang.Boolean.parseBoolean(str), false)
            }
            if (isInteger(str)) {
                return Variable(Integer.parseInt(str).toLong(), false)
            }
            return if (isDouble(str)) {
                Variable(java.lang.Double.parseDouble(str), false)
            } else Variable(str, false)

        }

        fun isBoolean(str: String): Boolean {
            val toLower = str.toLowerCase()

            return toLower == "true" || toLower == "false"
        }

        fun isDouble(str: String): Boolean {
            //        boolean dotCounted = false;
            //
            //        for(int i = 0; i < str.length(); i++) {
            //            char ch = str.charAt(i);
            //            if('9' < ch || ch < '0') {
            //                if(ch == '.') {
            //                    if(dotCounted) {
            //                        return false;
            //                    }
            //                    dotCounted = true;
            //                }
            //                return false;
            //            }
            //        }
            //
            //        return true;
            try {
                java.lang.Double.parseDouble(str)
                return true
            } catch (ex: Exception) {
                return false
            }

        }

        fun isInteger(str: String): Boolean {
            try {
                Integer.parseInt(str)
                return true
            } catch (ex: Exception) {
                return false
            }

            //        for(int i = 0; i < str.length(); i++) {
            //            char ch = str.charAt(i);
            //            if('9' < ch || ch < '0') {
            //                return false;
            //            }
            //        }
            //
            //        return true;
        }
    }
}
