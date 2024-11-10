val javaVersion = 21
val silkVersion = "1.10.7"
val polymerVersion = "0.9.17+1.21.1"

plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
    alias(libs.plugins.fabricLoom)
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

    modImplementation(libs.bundles.fabric)

    modImplementation(libs.bundles.silk)

    include(dependency(libs.alert))
    include(dependency(libs.kotlinxSerializationJson))

    // Database
    includeFromToml(libs.bundles.mongodb)

    // Adventure
    implementation(libs.bundles.kyoriAdventure)
    modImplementation(libs.kyoriAdventurePlatformFabric)

    // Discord
    includeFromToml(libs.bundles.jda)

    // Polymer
    includeFromToml(libs.bundles.polymer)
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

/**
 * Adds a dependency (by the provider) to the project.
 *
 * @param provider The provider of the dependency.
 *
 * @return The dependency as Any.
 */
fun DependencyHandler.dependency(provider: Provider<MinimalExternalModuleDependency>): Any {
    return implementation(provider.get().group, provider.get().name, provider.get().version)
}

/**
 * Adds a dependency (by the provider) to the project.
 *
 * @param provider The provider of the dependency.
 * @param implement Whether to implement the dependency.
 *
 * @return The dependency as Any.
 */
fun DependencyHandler.includeFromToml(provider: Provider<ExternalModuleDependencyBundle>, implement: Boolean = true): Dependency? {
    if (implement) {
        implementation(provider)
    }
    return include(provider)
}