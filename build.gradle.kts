val javaVersion = 21
val silkVersion = "1.10.5"

plugins {
    kotlin("jvm") version "1.9.23"
    id("fabric-loom") version "1.6-SNAPSHOT"
}

group = "live.einfachgustaf"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:1.20.6")
    mappings("net.fabricmc:yarn:1.20.6+build.1")
    modImplementation("net.fabricmc:fabric-loader:0.15.10")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.97.8+1.20.6")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.10.19+kotlin.1.9.23")

    modImplementation("net.silkmc:silk-core:$silkVersion")
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjdk-release=$javaVersion", "-Xskip-prerelease-check")
            jvmTarget = "$javaVersion"
        }
    }
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

loom {
    serverOnlyMinecraftJar()
}