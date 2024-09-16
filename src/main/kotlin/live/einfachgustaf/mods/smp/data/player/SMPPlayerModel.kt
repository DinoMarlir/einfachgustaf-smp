package live.einfachgustaf.mods.smp.data.player

import kotlinx.serialization.Serializable

@Serializable
data class SMPPlayerModel(
    val uuid: String,
    var lastKnownName: String? = null,
    var discordId: String? = null,
    var advancements: Set<String> = emptySet()
)