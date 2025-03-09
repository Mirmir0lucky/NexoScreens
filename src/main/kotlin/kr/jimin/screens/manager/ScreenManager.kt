package kr.jimin.screens.manager

import kr.jimin.screens.config.ConfigHandler
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class ScreenManager(private val plugin: JavaPlugin) {
    private val activeScreens: MutableMap<UUID, ScreenView> = mutableMapOf()
    private val screens: MutableMap<String, Screen> = mutableMapOf()
    private val configHandler = ConfigHandler(plugin)

    fun show(players: Set<Player>, screen: Screen, color: TextColor, text: Component, fadeInTicks: Long, stayTicks: Long, fadeOutTicks: Long) {
        if (!Bukkit.isPrimaryThread()) {
            plugin.server.scheduler.runTask(plugin) { _ -> show(players, screen, color, text, fadeInTicks, stayTicks, fadeOutTicks) }
            return
        }

        val show = mutableSetOf(*players.toTypedArray())
        show.removeIf { activeScreens.containsKey(it.uniqueId) }
        screen.show(show, color, text, fadeInTicks, stayTicks, fadeOutTicks) { uuid, view -> activeScreens[uuid] = view }
    }

    fun reload() {
        if (!Bukkit.isPrimaryThread()) {
            plugin.server.scheduler.runTask(plugin) { _ -> reload() }
            return
        }

        clear()
        configHandler.loadConfig()
        screens.putAll(configHandler.parseScreens())
    }

    fun clear() {
        activeScreens.forEach { (uuid, view) ->
            val player = plugin.server.getPlayer(uuid) ?: return@forEach
            player.clearTitle()
            view.executeEndCommands(player)
            player.isInvulnerable = view.isInvulnerable
        }
        activeScreens.clear()
        screens.clear()
    }

    fun tickScreens() {
        activeScreens.keys.removeIf { plugin.server.getPlayer(it) == null }
        activeScreens.filterValues(ScreenView::isOver).forEach { (uuid, view) ->
            val player = plugin.server.getPlayer(uuid) ?: return@forEach
            view.executeEndCommands(player)
            player.isInvulnerable = view.isInvulnerable
            activeScreens.remove(uuid)
        }
    }

    fun getScreen(id: String): Screen? = screens[id]
    fun getActiveScreen(uuid: UUID): ScreenView? = activeScreens[uuid]
    fun removeActiveScreen(uuid: UUID) = activeScreens.remove(uuid)
    fun getActiveScreens(): Map<UUID, ScreenView> = activeScreens.toMap()
    fun getScreenKeys(): Set<String> = screens.keys
}