package kr.jimin.screens.config

import com.nexomc.nexo.commands.ReloadCommand
import kr.jimin.screens.manager.Screen
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Files

class ConfigHandler(private val plugin: JavaPlugin) {

    fun loadConfig() {
        plugin.saveDefaultConfig()
        plugin.reloadConfig()
        saveResources()
    }

    fun parseScreens(): Map<String, Screen> {
        val screens = mutableMapOf<String, Screen>()
        val defaults = plugin.config.getConfigurationSection("defaults") ?: return screens
        val screensSection = plugin.config.getConfigurationSection("screens") ?: return screens

        screensSection.getKeys(false).forEach { key ->
            screensSection.getConfigurationSection(key)?.let { section ->
                screens[key] = Screen.parse(key, section, defaults)
            }
        }
        return screens
    }

    private fun saveResources() {
        val dir = File(plugin.server.pluginsFolder, "Nexo")
        val glyphFile = File(dir, "glyphs/nexoscreens.yml")
        val packFile = File(dir, "pack/external_packs/nexoscreens.zip")

        var changed = false
        if (!glyphFile.exists()) {
            glyphFile.parentFile.mkdirs()
            plugin.getResource("default/nexoscreens.yml")?.let { Files.copy(it, glyphFile.toPath()) }
            changed = true
        }

        if (!packFile.exists()) {
            packFile.parentFile.mkdirs()
            plugin.getResource("default/assets.zip")?.let { Files.copy(it, packFile.toPath()) }
            changed = true
        }

        if (changed && plugin.server.pluginManager.getPlugin("Nexo")?.isEnabled == true) {
            ReloadCommand.reloadAll(plugin.server.consoleSender)
        }
    }
}