package live.einfachgustaf.mods.smp.teams

import kotlinx.serialization.Serializable

/**
 * Represents a team in the SMP.
 *
 * @param uuid The UUID of the team.
 * @param name The name of the team.
 * @param color The color of the team.
 */
@Serializable
data class SMPTeam(
    val uuid: String,
    val name: String,
    val color: String
)