package live.einfachgustaf.mods.smp.teams.impl

import live.einfachgustaf.mods.smp.teams.AbstractSMPTeam
import java.util.*

class SMPTeamImpl(
    private val uuid: String, // TODO: Change to UUID, it's becuase of the serialization
    private var name: String,
    private var color: String
): AbstractSMPTeam() {

    override fun uuid(): UUID = UUID.fromString(uuid)

    override fun name(): String = name

    override fun name(name: String) {
        this.name = name
    }

    override fun color(): String {
        return color
    }

    override fun color(color: String) {
        this.color = color
    }

    @Deprecated(
        message = "This is only an temporary solution. When the SMPPlayer gets introduced, this will be removed."
    )
    override fun players(): List<UUID> {
        TODO("Not yet implemented")
    }

    override fun addPlayer(uuid: UUID) {
        TODO("Not yet implemented")
    }

    override fun removePlayer(uuid: UUID) {
        TODO("Not yet implemented")
    }

    override fun update() {
        TODO("Not yet implemented")
    }
}