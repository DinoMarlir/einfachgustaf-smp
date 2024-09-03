val javaVersion = 21
val silkVersion = "1.10.7"

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("fabric-loom") version "1.7-SNAPSHOT"
}

group = "live.einfachgustaf"
version = "0.1+1.21"

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:1.21")
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc:fabric-loader:0.15.11")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.100.4+1.21")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.11.0+kotlin.2.0.0")

    modImplementation("net.silkmc:silk-core:$silkVersion")
    modImplementation("net.silkmc:silk-commands:$silkVersion")

    include(implementation("me.obsilabor", "alert", "1.0.8"))
    include(implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")!!)

    // Database
    include(implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.1.0")!!)
    include(implementation("org.mongodb:bson-kotlinx:5.1.0")!!)

    // Discord
    include(implementation("net.dv8tion:JDA:5.1.0")!!)
    include(implementation("club.minnced:jda-ktx:0.12.0")!!)
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