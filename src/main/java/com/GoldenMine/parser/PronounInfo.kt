package com.GoldenMine.parser

import com.GoldenMine.parser.pronoun.IPronoun

class PronounInfo(val modifying: Sentence, val modifyingIndex: Int,
                val modified: Sentence, val modifiedIndex: Int,
                 val context: Context, val variableIndex: Int, val modifiedKey: String, val modifiedKeyPronoun: IPronoun)

/*
class ProNounInfo {
        private Sentence modifying;
        private int modifyingIndex;

        private Sentence modified;
        private int modifiedIndex;
        private String modifiedKey;
        private IPronoun modifiedKeyPronoun;
    }
 */