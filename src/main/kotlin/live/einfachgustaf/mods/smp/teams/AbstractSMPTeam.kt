package live.einfachgustaf.mods.smp.teams

import java.util.UUID

abstract class AbstractSMPTeam {
    abstract fun uuid(): UUID

    abstract fun name(): String
    abstract fun name(name: String)

    abstract fun color(): String
    abstract fun color(color: String)

    abstract fun update()

    fun serializable() = SerializableSMPTeam(uuid().toString(), name(), color())
}