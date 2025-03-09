package kr.jimin.screens.util

import org.bukkit.Bukkit

object CommandUtils {
//    fun replace(strings: List<String>, placeholders: Map<String, String>): List<String> {
//        return strings.map { str ->
//            var result = str
//            placeholders.forEach { (key, value) -> result = result.replace(key, value) }
//            result
//        }
//    }

    fun runCommands(commands: List<String>) {
        val sender = Bukkit.getConsoleSender()
        commands.forEach { Bukkit.dispatchCommand(sender, it) }
    }
}