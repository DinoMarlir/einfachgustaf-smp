package live.einfachgustaf.mods.smp.features

import kotlinx.serialization.Serializable

object StatusRegistry {
    
}

@Serializable
data class Status(
    val name: String,
    val color: Int
)