package live.einfachgustaf.mods.smp.utils

import kotlinx.serialization.json.Json
import java.io.File

val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}

val dataFolder: File get() {
    val file = File("config/einfachgustaf-smp/")
    if (!file.exists()) {
        file.mkdirs()
    }

    return file
}