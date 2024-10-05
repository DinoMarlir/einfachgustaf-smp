package live.einfachgustaf.mods.smp.teams.impl

import live.einfachgustaf.mods.smp.teams.AbstractSMPTeam
import live.einfachgustaf.mods.smp.teams.AbstractSMPTeamProvider

/**
 * Represents a team provider in the SMP.
 */
class SMPTeamProviderImpl: AbstractSMPTeamProvider() {

    override fun team(uuid: String): AbstractSMPTeam {
        TODO("Not yet implemented")
    }

    override fun teams(): List<AbstractSMPTeam> {
        TODO("Not yet implemented")
    }

    override fun createTeam(name: String, color: String?): AbstractSMPTeam {
        TODO("Not yet implemented")
    }

    override fun deleteTeam(uuid: String) {
        TODO("Not yet implemented")
    }

    override fun updateTeam(team: AbstractSMPTeam) {
        TODO("Not yet implemented")
    }
}