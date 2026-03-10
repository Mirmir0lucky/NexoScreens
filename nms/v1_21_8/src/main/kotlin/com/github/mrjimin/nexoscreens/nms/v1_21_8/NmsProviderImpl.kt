package com.github.mrjimin.nexoscreens.nms.v1_21_8

import com.github.mrjimin.nexoscreens.api.NmsProvider
import net.minecraft.network.protocol.game.ClientboundGameEventPacket
import org.bukkit.GameMode
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player

class NmsProviderImpl : NmsProvider {

    override fun hideHUD(player: Player) {
        if (player.gameMode == GameMode.SPECTATOR) return

        val craftPlayer = player as CraftPlayer
        val packet = ClientboundGameEventPacket(
            ClientboundGameEventPacket.CHANGE_GAME_MODE,
            3f // Spectator
        )
        craftPlayer.handle.connection.send(packet)
    }

    override fun showHUD(player: Player) {
        if (player.gameMode == GameMode.SPECTATOR) return
        
        val craftPlayer = player as CraftPlayer
        val packet = ClientboundGameEventPacket(
            ClientboundGameEventPacket.CHANGE_GAME_MODE,
            gamemodeToId(player.gameMode)
        )
        craftPlayer.handle.connection.send(packet)
    }

    private fun gamemodeToId(gameMode: GameMode): Float {
        return when (gameMode) {
            GameMode.SURVIVAL -> 0f
            GameMode.CREATIVE -> 1f
            GameMode.ADVENTURE -> 2f
            GameMode.SPECTATOR -> 3f
        }
    }
}
