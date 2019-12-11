package kr.goldenmine

import kr.goldenmine.parser.VariableStorage
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin



class ScriptRunnerMinecraft : JavaPlugin(), Listener {

    override fun onEnable() {
        Bukkit.broadcastMessage("§cScriptRunner for minecraft is enabled")
        Bukkit.broadcastMessage("§cLoading Variables...")
    }

    override fun onDisable() {
        Bukkit.broadcastMessage("§cScriptRunner for minecraft is disabled")
    }

    @EventHandler
    fun test(ev: BlockBreakEvent) {

    }
}

fun saveVariables(storage: VariableStorage) {

}

class DataSaveThread(storage: VariableStorage, ms: Int) : Thread() {
    var key = Any()
    var isStop = true

    override fun run() {
        while(isStop) {

        }
    }

    fun stopThread() {
        isStop = false
        interrupt()
    }
}