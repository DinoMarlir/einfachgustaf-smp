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