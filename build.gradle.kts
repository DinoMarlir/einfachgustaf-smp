val javaVersion = 21
val silkVersion = "1.10.7"
val polymerVersion = "0.9.17+1.21.1"

plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
    id("fabric-loom") version "1.8.10"
}

group = "live.einfachgustaf"
version = "0.1+1.21.1"

repositories {
    mavenCentral()
    maven("https://maven.nucleoid.xyz") // For Polymer
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:0.16.5")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.105.0+1.21.1")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.12.2+kotlin.2.0.20")

    modImplementation("net.silkmc:silk-core:$silkVersion")
    modImplementation("net.silkmc:silk-commands:$silkVersion")
    modImplementation("net.silkmc:silk-igui:$silkVersion")

    include(implementation("me.obsilabor", "alert", "1.0.8"))
    include(implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")!!)

    // Database
    include(implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.2.1")!!)
    include(implementation("org.mongodb:bson-kotlinx:5.2.1")!!)

    // Adventure
    implementation("net.kyori:adventure-api:4.17.0")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    modImplementation("net.kyori:adventure-platform-fabric:5.14.1")

    // Discord
    include(implementation("net.dv8tion:JDA:5.2.1")!!)
    include(implementation("club.minnced:jda-ktx:0.12.0")!!)

    // Polymer
    modImplementation("eu.pb4:polymer-core:$polymerVersion")
    modImplementation("eu.pb4:polymer-resource-pack:$polymerVersion")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(javaVersion)
    }
    processResources {
        val properties = mapOf("version" to project.version)
        inputs.properties(properties)
        filesMatching("fabric.mod.json") { expand(properties) }
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjdk-release=$javaVersion", "-Xskip-prerelease-check")
        jvmToolchain(javaVersion)
    }
}