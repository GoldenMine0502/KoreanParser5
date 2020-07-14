package kr.goldenmine.parser.postposition

import java.util.*

object JosaStorage {

    private val josaMap = HashMap<JosaCommunity, MutableList<IPostPosition>>()
    private val allJosaList = ArrayList<IPostPosition>()

    init {
        init()
    }

    private fun init() {
        addJosa(IPostPosition서술어())
        addJosa(IPostPosition주어())
        addJosa(IPostPosition목적어())
        addJosa(IPostPosition접속조사())
        addJosa(IPostPosition부사어로())
        addJosa(IPostPosition부사어에())
        addJosa(IPostPosition부사어에서())
        addJosa(IPostPosition까지())
        addJosa(IPostPosition비교())
        addJosa(IPostPosition소유격())
        addJosa(IPostPosition분류없음())
    }

    fun getAllJosaList(): List<IPostPosition> {
        return allJosaList
    }

    fun getJosaList(community: JosaCommunity): MutableList<IPostPosition> {
        return ArrayList(josaMap[community]!!)
    }

    fun addJosa(josa: IPostPosition) {
        val josaList: MutableList<IPostPosition>
        if (josaMap.containsKey(josa.josaCommunity)) {
            josaList = josaMap[josa.josaCommunity]!!
        } else {
            josaList = ArrayList()
            josaMap[josa.josaCommunity] = josaList
        }

        josaList.add(josa)
        allJosaList.add(josa)
    }

    fun getGyukJosaListWithBojo(): List<IPostPosition> {
        val list = getJosaList(JosaCommunity.격조사)
        list.addAll(getJosaList(JosaCommunity.보조사))

        return list
    }
}
