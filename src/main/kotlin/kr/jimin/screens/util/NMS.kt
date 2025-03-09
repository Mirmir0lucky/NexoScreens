package kr.jimin.screens.util

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import kr.jimin.screens.NexoScreens
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Scoreboard
import java.util.*

object NMS {
    private var scoreboards: WeakHashMap<UUID, Scoreboard> = WeakHashMap()
    private var changeGamemode: Any? = null

    init {
        try {
            val fields = PacketType.Play.Server.GAME_STATE_CHANGE.packetClass.declaredFields
            if (fields[0].type != fields[1].type) changeGamemode = fields[4][null]
            else changeGamemode = fields[3][null]
        } catch (e: IllegalAccessException) {
            System.err.println("Failed to load ScreenEffects NMS.")
            e.printStackTrace()
        }
    }

    private fun setGamemode(player: Player?, gamemode: Float) {
        val packet = PacketContainer(PacketType.Play.Server.GAME_STATE_CHANGE)
        packet.modifier.write(0, changeGamemode)
        packet.float.write(0, gamemode)
        sendPacket(player, packet)
    }

    private fun refreshAbilities(player: Player) {
        player.allowFlight = player.allowFlight
    }

    fun hideHUD(player: Player) {
        scoreboards[player.uniqueId] = player.scoreboard
        player.scoreboard = Bukkit.getScoreboardManager().newScoreboard

        if (player.gameMode == GameMode.SPECTATOR) return

        setGamemode(player, gamemodeToId(GameMode.SPECTATOR))
        refreshAbilities(player)
    }

    fun showHUD(player: Player) {
        val scoreboard = scoreboards[player.uniqueId]
        if (scoreboard != null) player.scoreboard = scoreboard

        if (player.gameMode == GameMode.SPECTATOR) return
        setGamemode(player, gamemodeToId(player.gameMode))
        Bukkit.getScheduler().runTaskLater(
            NexoScreens.INSTANCE,
            Runnable { refreshAbilities(player) }, 5L
        )
    }

    private fun gamemodeToId(gameMode: GameMode): Float {
        return when (gameMode) {
            GameMode.SURVIVAL -> 0f
            GameMode.CREATIVE -> 1f
            GameMode.ADVENTURE -> 2f
            GameMode.SPECTATOR -> 3f
        }
    }

    private fun sendPacket(player: Player?, packet: PacketContainer?) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet)
        } catch (ignored: Throwable) {
        }
    }
}