package live.einfachgustaf.mods.smp.utils

import kotlinx.serialization.json.Json
import net.kyori.adventure.text.minimessage.MiniMessage

val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}

val miniMessage = MiniMessage.miniMessage()