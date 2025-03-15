plugins {
    kotlin("jvm") version "2.1.0"
    id("io.github.goooler.shadow") version "8.1.8"
}

group = "kr.jimin.screens"
version = "1.0.0"
description = "NexoScreens"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") // Paper
    maven("https://repo.nexomc.com/releases") // Nexo
    maven("https://repo.dmulloy2.net/repository/public/") // ProtocolLib
    maven("https://repo.extendedclip.com/releases/") // PlaceholderAPI
}

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.21.1-R0.1-SNAPSHOT") // Paper
    compileOnly("com.nexomc","nexo", "1.0.0") { exclude("*", "*") }// Nexo
    compileOnly("com.comphenix.protocol","ProtocolLib","5.3.0") // ProtocolLib
    compileOnly("me.clip","placeholderapi","2.11.6") // PlaceholderAPI

    implementation("org.bstats","bstats-bukkit","3.0.2") // bStats
}

tasks {
    processResources {
        val props = mapOf(
            "name" to rootProject.name,
            "version" to rootProject.version,
            "author" to "MrJimin"
        )
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }

    shadowJar {
        relocate("org.bstats", "kr.jimin.screens.libs.bstats")
        exclude("META-INF/**")
        exclude("kotlin/**")
        exclude("kotlinx/**")
        exclude("org/**")
        from(rootProject.file("LICENSE"))
    }

    build {
        dependsOn(shadowJar)
        doLast {
            copy {
                from(shadowJar.get().archiveFile)
                // into("C:\\Users\\aa010\\Desktop\\TestItemsadder\\plugins")
                into("C:\\Users\\aa010\\Desktop\\Nexo\\plugins")
                // into("C:\\Users\\aa010\\Desktop\\Grass\\plugins")
                rename { "${rootProject.name}-${rootProject.version}.jar" }
            }
        }
    }

}
kotlin {
    jvmToolchain(21)
}