package live.einfachgustaf.mods.smp.data.player

import kotlinx.coroutines.launch
import net.silkmc.silk.core.task.mcCoroutineScope
import java.util.UUID

class SMPPlayer(val uuid: UUID, val username: String) {
    val advancements = mutableSetOf<String>()

    fun update() = mcCoroutineScope.launch {
        val serializable = PlayerRepository.getPlayerOrCreate(uuid)

        serializable.let {
            it.lastKnownName = username
            it.advancements = advancements
        }

        PlayerRepository.updatePlayer(serializable)
    }
}