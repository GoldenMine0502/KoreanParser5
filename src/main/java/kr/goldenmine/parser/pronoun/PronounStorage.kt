package kr.goldenmine.parser.pronoun

import java.util.*

class PronounStorage private constructor() {

    private val pronouns = HashMap<String, IPronoun>()

    init {
        init()
    }

    private fun init() {
        addPronoun(IPronoun것())
        addPronoun(IPronoun걸())
        addPronoun(IPronoun값())
        addPronoun(IPronoun나머지())
        addPronoun(IPronoun때())
    }

    fun getPronoun(key: String): IPronoun? {
        return pronouns[key]
    }

    fun hasPronoun(key: String?): Boolean {
        return pronouns.containsKey(key)
    }

    fun addPronoun(pronoun: IPronoun) {
        pronouns[pronoun.type] = pronoun
    }

    companion object {
        val INSTANCE = PronounStorage()
    }
}