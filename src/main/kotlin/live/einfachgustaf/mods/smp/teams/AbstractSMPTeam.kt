package live.einfachgustaf.mods.smp.teams

import java.util.UUID

/**
 * Represents a team in the SMP.
 */
abstract class AbstractSMPTeam {

    /**
     * The UUID of the team.
     *
     * @return The UUID of the team.
     */
    abstract fun uuid(): UUID

    /**
     * The name of the team.
     *
     * @return The name of the team.
     */
    abstract fun name(): String

    /**
     * Sets the name of the team.
     *
     * @param name The name of the team.
     */
    abstract fun name(name: String)

    /**
     * The color of the team.
     *
     * @return The color of the team.
     */
    abstract fun color(): String

    /**
     * Sets the color of the team.
     *
     * @param color The color of the team.
     */
    abstract fun color(color: String)

    /**
     * The players in the team.
     *
     * @since 0.1+1.21.1
     * @return The players in the team.
     */
    @Deprecated(
        message = "This is only an temporary solution. When the SMPPlayer gets introduced, this will be removed."
    )
    abstract fun players(): List<UUID>

    /**
     * Adds a player to the team.
     *
     * @param uuid The UUID of the player.
     */
    abstract fun addPlayer(uuid: UUID)

    /**
     * Removes a player from the team.
     *
     * @param uuid The UUID of the player.
     * @throws PlayerNotInTeamException If the player is not in the team.
     */
    abstract fun removePlayer(uuid: UUID)

    /**
     * Updates the team.
     */
    abstract fun update()

    /**
     * Serializes the team.
     *
     * @return The serialized team.
     */
    fun serializable() = SerializableSMPTeam(uuid().toString(), name(), color())
}