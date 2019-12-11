package kr.goldenmine

import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class EventListener(val codeStorage: CodeStorage) : Listener {

    @EventHandler
    fun onBreak(ev: BlockBreakEvent) {
        runScript(ev)
    }

    @EventHandler
    fun onPlace(ev: BlockPlaceEvent) {
        runScript(ev)
    }

    fun runScript(ev: Event) {
        val metadata = ArrayList<Any>()
        metadata.add(ev)

        codeStorage.codeMap[ev.eventName]!!.forEach {
            it.perform(metadata)
        }
    }
}