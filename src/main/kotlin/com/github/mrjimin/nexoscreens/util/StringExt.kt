package com.github.mrjimin.nexoscreens.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

private val miniMessage = MiniMessage.miniMessage()
private val colorReplacements = mapOf(
    "§a" to "<green>",
    "§c" to "<red>",
    "§b" to "<aqua>",
    "§e" to "<yellow>",
    "§6" to "<gold>",
    "§d" to "<light_purple>",
    "§f" to "<white>",
    "§3" to "<dark_aqua>",
    "§9" to "<blue>",
    "§7" to "<gray>",
    "§8" to "<dark_gray>",
    "§4" to "<dark_red>",
    "§1" to "<dark_blue>",
    "§2" to "<dark_green>",
    "§5" to "<dark_purple>"
)

fun String.toMMComponent(): Component {
    var result = replace("&", "§")
    colorReplacements.forEach { (old, new) ->
        result = result.replace(old, new)
    }
    return miniMessage.deserialize(result)
}

fun String.toComponent(): Component {
    return Component.text(replace("&", "§"))
}