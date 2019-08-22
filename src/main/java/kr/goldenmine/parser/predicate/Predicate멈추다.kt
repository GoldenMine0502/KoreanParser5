package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.parser.Variable
import kr.goldenmine.parser.VariableStorage
import java.util.HashMap

open class Predicate멈추다 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true)
    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val defaultSentence: String
        get() = "멈추다"
    override val neededSentenceElements: List<String>
        get() = listOf("목적어")
    override val neededSentenceElementTypes: List<ValueScope>
        get() = listOf(ValueScope.STRING)
    override val optionalSentenceElements: List<String>
        get() = listOf()
    override val optionalSentenceElementTypes: List<ValueScope>
        get() = listOf()

    override fun perform(sentence: Sentence, metadata: List<Any>?, local: VariableStorage): Variable? {
        val 목적어 = sentence.map["목적어"]!!.variables!!
        for(value in 목적어) {
            if(value != null) {
                val key = value.stringValue()
                if(key != null) {
                    val timeSb = StringBuilder()
                    val unitSb = StringBuilder()
                    var changed = false

                    for(index in key.indices) {
                        val ch = key[index]
                        if(!changed && (ch in '0'..'9' || ch == '.')) {
                            timeSb.append(ch)
                        } else if(ch != ' ') {
                            changed = true
                        }
                        if(changed) {
                            unitSb.append(ch)
                        }
                    }

                    val time = timeSb.toString().toDouble()

                    if(unitSb.endsWith(" 동안")) {
                        unitSb.setLength(unitSb.length - 3)
                    }
                    if(unitSb.endsWith("동안")) {
                        unitSb.setLength(unitSb.length - 2)
                    }
                    when(unitSb.toString()) {
                        "나노초", "ns", "nano", "nanosecond", "nanoseconds", "나노",
                        "나노세컨드", "나노새컨드", "나노 세컨드", "나노 새컨드" -> { Thread.sleep(time.toLong()) }
                        "밀리초", "ms", "mili", "milimeter", "millimeter", "milisecond",
                        "miliseconds", "밀리", "밀리세컨드", "밀리새컨드", "밀리 세컨드", "밀리 새컨드" -> { Thread.sleep(time.toLong()) }
                        "초", "s", "sec", "second", "seconds" -> { Thread.sleep((time*1000).toLong()) }
                        "분", "m", "min", "minute", "minutes" -> { Thread.sleep((time*1000*60).toLong()) }
                        "시", "시간", "h", "hour", "hours" -> { Thread.sleep((time*1000*60*60).toLong()) }
                        "일", "날", "d", "day", "days", "date" -> { Thread.sleep((time*1000*60*60*24).toLong()) }
                        //"개월", "mm", "mon", "month", "months" -> { Thread.sleep((time*1000*60*60*24*30).toLong()) }
                        //"년", "mm", "mon", "month", "months" -> { Thread.sleep((time*1000*60*60*24*30).toLong()) }

                    }
                }
            }
        }
        return null
    }

    override fun isAccord(contexts: HashMap<String, Context>, metadata: List<Any>?): Boolean {
        return true
    }

}