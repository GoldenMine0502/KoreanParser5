package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.Context
import kr.goldenmine.parser.Sentence
import kr.goldenmine.objects.Variable
import kr.goldenmine.parser.VariableStorage
import java.util.HashMap


const val MS = 1000
const val MIN = 60
const val SEC = 60
const val DAY = 24
const val MONTH = 30
const val YEAR = 365

open class Predicate멈추다 : IPredicate {
    override val neededReplaceVariable: List<Boolean>
        get() = listOf(true)
    override val optionalReplaceVariable: List<Boolean>
        get() = listOf()

    override val neededSetters: List<Boolean>
        get() = listOf(false)

    override val optionalSetters: List<Boolean>
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
                        "나노세컨드", "나노새컨드", "나노 세컨드", "나노 새컨드"
                        -> { Thread.sleep(time.toLong()) }
                        "밀리초", "ms", "mili", "milimeter", "millimeter", "milisecond",
                        "miliseconds", "밀리", "밀리세컨드", "밀리새컨드", "밀리 세컨드", "밀리 새컨드"
                        -> { Thread.sleep(time.toLong()) }
                        "초", "s", "sec", "second", "seconds"
                        -> { Thread.sleep((time * MS).toLong()) }
                        "분", "m", "min", "minute", "minutes"
                        -> { Thread.sleep((time * MS * SEC).toLong()) }
                        "시", "시간", "h", "hour", "hours"
                        -> { Thread.sleep((time * MS * SEC * MIN).toLong()) }
                        "일", "날", "d", "day", "days", "date"
                        -> { Thread.sleep((time * MS * SEC * MIN * DAY).toLong()) }
                        "개월", "mm", "mon", "month", "months"
                        -> { Thread.sleep((time * MS * SEC * MIN * DAY * MONTH).toLong()) }
                        "년", "yy", "y", "year", "years"
                        -> { Thread.sleep((time * MS * SEC * MIN * DAY * YEAR).toLong()) }
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