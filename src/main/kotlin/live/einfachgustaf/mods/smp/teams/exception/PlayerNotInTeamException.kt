package live.einfachgustaf.mods.smp.teams.exception

import live.einfachgustaf.mods.smp.teams.AbstractSMPTeam

class PlayerNotInTeamException(team: AbstractSMPTeam): Exception("Player is not in the team '${team.name()}'")