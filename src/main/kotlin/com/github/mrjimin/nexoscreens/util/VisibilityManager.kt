package com.github.mrjimin.nexoscreens.util

import com.github.mrjimin.nexoscreens.NexoScreens
import com.github.mrjimin.nexoscreens.manager.NmsManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Scoreboard
import java.util.*

object VisibilityManager {
    private var scoreboards: WeakHashMap<UUID, Scoreboard> = WeakHashMap()

    fun hideHUD(player: Player) {
        scoreboards[player.uniqueId] = player.scoreboard
        player.scoreboard = Bukkit.getScoreboardManager().newScoreboard

        NmsManager.provider.hideHUD(player)
        refreshAbilities(player)
    }

    fun showHUD(player: Player) {
        val scoreboard = scoreboards[player.uniqueId]
        if (scoreboard != null) player.scoreboard = scoreboard

        NmsManager.provider.showHUD(player)
        Bukkit.getScheduler().runTaskLater(
            NexoScreens.INSTANCE,
            Runnable { refreshAbilities(player) }, 5L
        )
    }

    private fun refreshAbilities(player: Player) {
        player.allowFlight = player.allowFlight
    }
}
