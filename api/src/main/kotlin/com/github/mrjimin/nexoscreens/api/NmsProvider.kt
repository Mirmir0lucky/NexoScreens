package com.github.mrjimin.nexoscreens.api

import org.bukkit.entity.Player

interface NmsProvider {
    fun hideHUD(player: Player)
    fun showHUD(player: Player)
}
