package kr.jimin.screens.listener

import kr.jimin.screens.manager.ScreenManager
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent

class EventListener(private val screenManager: ScreenManager) : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        val uuid = player.uniqueId
        val view = screenManager.getActiveScreen(uuid) ?: return
        view.executeEndCommands(player)
        player.isInvulnerable = view.isInvulnerable
        screenManager.removeActiveScreen(uuid)
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onMove(event: PlayerMoveEvent) {
        val uuid = event.player.uniqueId
        val view = screenManager.getActiveScreen(uuid) ?: return
        if (view.isOver() || view.movement) return
        event.isCancelled = true
    }
}