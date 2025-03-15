package kr.jimin.screens.util

import kr.jimin.screens.NexoScreens
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object CommandUtils {

    val TIME_SUGGESTIONS = arrayOf("5", "10", "20", "40", "60", "80", "100")
    val COLOR_SUGGESTIONS = arrayOf("#0000ff", "#000000", "#FFFFFF")
    val DEFAULT_TEXT_SUGGESTION = "<green>Test"

    // 플레이어 파싱
    fun parsePlayers(rawPlayer: String): MutableSet<Player>? {
        val players = mutableSetOf<Player>()
        return if (rawPlayer == "*") {
            players.addAll(Bukkit.getOnlinePlayers())
            players
        } else {
            Bukkit.getPlayer(rawPlayer)?.let { players.add(it); players }
        }
    }

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