package live.einfachgustaf.mods.smp.teams

/**
 * Represents a team in the SMP.
 */
abstract class AbstractSMPTeamProvider {

    /**
     * Gets a team by its UUID.
     *
     * @param uuid The UUID of the team.
     * @return The team with the UUID.
     */
    abstract fun team(uuid: String): AbstractSMPTeam

    /**
     * Gets all teams.
     *
     * @return All teams.
     */
    abstract fun teams(): List<AbstractSMPTeam>

    /**
     * Creates a team.
     *
     * @param name The name of the team.
     * @param color The color of the team.
     * @return The created team.
     */
    abstract fun createTeam(name: String, color: String? = null): AbstractSMPTeam

    /**
     * Deletes a team by its UUID.
     *
     * @param uuid The UUID of the team.
     */
    abstract fun deleteTeam(uuid: String)

    /**
     * Updates a team.
     *
     * @param team The team to update.
     */
    abstract fun updateTeam(team: AbstractSMPTeam)
}