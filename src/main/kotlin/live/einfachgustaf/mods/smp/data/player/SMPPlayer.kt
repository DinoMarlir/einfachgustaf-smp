package live.einfachgustaf.mods.smp.data.player

import kotlinx.serialization.Serializable

@Serializable
data class SMPPlayer(
    val uuid: String,
    val lastKnownName: String,
    val discordId: String,
    val advancements: Set<String> = emptySet()
)