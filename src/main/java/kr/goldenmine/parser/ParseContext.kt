package kr.goldenmine.parser

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import kr.goldenmine.parser.predicate.PredicateStorage
import java.util.*

class ParseContext(source: String) {
    var source: String = source
        private set
    val parsedMap = HashMap<String, MutableList<Context>>()

    //private IParser parser;
    val sentences: MutableList<Sentence> = ArrayList()
    val sentenceDepth = ArrayList<Int>()

    val pronounInfoList: MutableList<PronounInfo> = ArrayList()
    //private HashMap<Sentence, IPredicateSpecific.MultiProcessData> dataMap = new HashMap<>();
    // 연결된 대명사 문장 실행 후 마지막 문장 실행
    var isNoParse: Boolean = false
        private set

    init {
        this.source = source

        preSourceModify()
        setNoParse()
    }

    private fun preSourceModify() {
        var index = 0

        while (source[index] == ' ' || source[index] == '\t') {
            index++
        }
        this.source = source.substring(index)

        if (source.endsWith(".")) {
            this.source = source.substring(0, source.length - 1)
        }
    }

    private fun setNoParse() {
        //PredicateStorage.INSTANCE.getNoParses();
        if (PredicateStorage.INSTANCE.noParses.contains(source)) {
            isNoParse = true
        }
    }

    companion object {
        //public static final Sentence DEFAULT_HASHMAP_KEY = new Sentence(null, null, null);


        val komoran = Komoran(DEFAULT_MODEL.LIGHT)

        init {
            komoran.setFWDic("user_data/fwd.user")
            komoran.setUserDic("user_data/dic.user")
        }
    }

    //    public HashMap<Sentence, IPredicateSpecific.MultiProcessData> getDataMap() {
    //        return dataMap;
    //    }
}
