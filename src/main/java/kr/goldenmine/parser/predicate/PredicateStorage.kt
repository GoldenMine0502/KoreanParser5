package kr.goldenmine.parser.predicate

import kr.goldenmine.parser.ParseContext
import kr.goldenmine.parser.predicatespecific.*
import java.util.*

class PredicateStorage private constructor() {
    private val predicates = HashMap<String, MutableList<IPredicate>>()
    private val predicateSpecifics = ArrayList<IPredicateSpecific>()
    val noParses = HashSet<String>()

    //
    //    public List<? extends IMultiProcessing> getMultis() {
    //        return predicateMultis;
    //    }

    val specifics: List<IPredicateSpecific>
        get() = predicateSpecifics

    init {
        init()
    }

    private fun init() {
        addPredicate(Predicate출력하다())
        addPredicate(Predicate설정하다())
        addPredicate(Predicate초기화하다())
        addPredicate(Predicate더하다())
        addPredicate(Predicate더하다2())
        addPredicate(Predicate빼다())
        addPredicate(Predicate곱하다())
        addPredicate(Predicate만들다객체())
        addPredicate(Predicate나누다())
        addPredicate(Predicate같다())
        addPredicate(Predicate크다())
        addPredicate(Predicate작다())
        addPredicate(Predicate멈추다())
        addPredicate(Predicate정지하다())
        addPredicate(Predicate제거하다())
        addPredicate(Predicate저장하다())

        addPredicateSpecific(SpecificIF())
        addPredicateSpecific(IPredicateSpecificPostAND())
        addPredicateSpecific(IPredicateSpecificPostOR())

        val 반복하다 = Predicate반복하다()

        addPredicate(반복하다)
        addPredicateSpecific(반복하다)
        //System.out.println("predicate add");
    }

    fun addPredicate(predicate: IPredicate) {
        val key = ParseContext.komoran.analyze(predicate.defaultSentence).list[0].first

        predicates.computeIfAbsent(key) { ArrayList() }.add(predicate)
    }

    //    public void addPredicateMulti(IMultiProcessing predicate) {
    //        predicateMultis.add(predicate);
    //    }
    fun addPredicateSpecific(specific: IPredicateSpecific) {
        predicateSpecifics.add(specific)
        noParses.addAll(specific.others)
    }

    fun getPredicate(str: String): List<IPredicate>? {
        //System.out.println(ParseContext.komoran.analyze(str).getList().get(0).getFirst());
        return predicates[getRoot(str)]
    }

//    fun getNoParses(): HashSet<out String> {
//        return noParses
//    }

    companion object {
        val INSTANCE = PredicateStorage()

        fun getRoot(str: String): String {
            return ParseContext.komoran.analyze(str).list[0].first
        }
    }
}
