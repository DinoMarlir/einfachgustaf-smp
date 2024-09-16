package live.einfachgustaf.mods.smp.player

import kotlinx.serialization.Serializable

@Serializable
data class SMPPlayer(
    val uuid: String,
    val lastKnownName: String,
    val discordId: String,
    val advancements: MutableList<String> = mutableListOf()
)