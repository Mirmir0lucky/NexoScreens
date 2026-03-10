package com.github.mrjimin.nexoscreens.manager

import com.github.mrjimin.nexoscreens.api.NmsProvider
import org.bukkit.Bukkit

object NmsManager {
    val provider: NmsProvider by lazy {
        val mcVersion = Bukkit.getMinecraftVersion()
        val className = when (mcVersion) {
            "1.21.8" -> "com.github.mrjimin.nexoscreens.nms.v1_21_8.NmsProviderImpl"
            else -> throw IllegalStateException("Unsupported server version: $mcVersion")
        }
        
        try {
            Class.forName(className).getDeclaredConstructor().newInstance() as NmsProvider
        } catch (e: Exception) {
            throw IllegalStateException("Failed to load NMS Provider for version: $mcVersion", e)
        }
    }
}
