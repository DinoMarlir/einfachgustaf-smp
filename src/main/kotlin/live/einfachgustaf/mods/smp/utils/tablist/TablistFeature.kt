package live.einfachgustaf.mods.smp.utils.tablist

import kotlinx.coroutines.Job
import live.einfachgustaf.mods.smp.extensions.audience
import live.einfachgustaf.mods.smp.internal.ReloadableRegistry
import live.einfachgustaf.mods.smp.utils.tablist.TablistConfig.Companion.config
import net.minecraft.server.MinecraftServer
import net.silkmc.silk.core.task.infiniteMcCoroutineTask
import kotlin.time.Duration.Companion.seconds


class TablistFeature(
    private val server: MinecraftServer
) {
    var job: Job? = null; private set

    fun startJob() {
        job = createJob().also { it.start() }
    }

    private fun createJob() = infiniteMcCoroutineTask(
        sync = false,
        period = config.get().updateTime.seconds
    ) {
        server.playerList.players.forEach {
            it.audience().sendPlayerListHeaderAndFooter(
                config.get().buildHeader(TablistPlaceholderResolver(it, server)),
                config.get().buildFooter(TablistPlaceholderResolver(it, server))
            )
        }
    }

    init {
        ReloadableRegistry.register(config)
    }
}