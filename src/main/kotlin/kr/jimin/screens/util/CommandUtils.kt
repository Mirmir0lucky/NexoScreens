package kr.jimin.screens.util

import kr.jimin.screens.NexoScreens
import org.bukkit.Bukkit

object CommandUtils {
//    fun replace(strings: List<String>, placeholders: Map<String, String>): List<String> {
//        return strings.map { str ->
//            var result = str
//            placeholders.forEach { (key, value) -> result = result.replace(key, value) }
//            result
//        }
//    }

/*
    fun runCommands(commands: List<String>) {
        val sender = Bukkit.getConsoleSender()
        commands.forEach { Bukkit.dispatchCommand(sender, it) }
    }
*/

    fun runCommands(commands: List<String>) {
        val sender = Bukkit.getConsoleSender()
        var commandDelayTicks = 0L
        val scheduler = Bukkit.getScheduler()

        commands.forEach { command ->
            if (command.startsWith("delay ")) {
                commandDelayTicks += (command.removePrefix("delay ").toLongOrNull() ?: 0L) * 20
            } else {
                scheduler.runTaskLater(NexoScreens.INSTANCE, Runnable {
                    Bukkit.dispatchCommand(sender, command)
                }, commandDelayTicks)
            }
        }
    }
}