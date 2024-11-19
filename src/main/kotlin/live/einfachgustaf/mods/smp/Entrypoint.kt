package live.einfachgustaf.mods.smp

import live.einfachgustaf.mods.smp.advancement.AdvancementRegistry
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.commands.smpReloadCommand
import live.einfachgustaf.mods.smp.data.db.MongoDB
import live.einfachgustaf.mods.smp.discord.DiscordBot
import live.einfachgustaf.mods.smp.polymer.PolymerItemRegistry
import live.einfachgustaf.mods.smp.polymer.PolymerResourceRegistry
import live.einfachgustaf.mods.smp.utils.tablist.TablistFeature
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.kyori.adventure.platform.fabric.FabricServerAudiences
import net.silkmc.silk.core.annotations.ExperimentalSilkApi
import net.silkmc.silk.core.event.Events
import net.silkmc.silk.core.event.Server
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val LOGGER: Logger = LogManager.getLogger("smp")
lateinit var audiences: FabricServerAudiences

fun initMain() = Unit

fun initServer() {
    // DATABASE
    MongoDB

    // Discord Bot
    DiscordBot

    // EVENTS
    ServerPlayConnectionEvents.JOIN.register { player, _, _ ->
        Advancements.createAdvancements(player.player)
    }

    // Listener
    postStart()

    // Polymer
    PolymerResourceRegistry
    PolymerItemRegistry

    // Commands
    smpReloadCommand()
}

@OptIn(ExperimentalSilkApi::class)
fun postStart() = Events.Server.postStart.listen {
    val server = it.server

    // KYORI AUDIENCE
    audiences = FabricServerAudiences.of(server)

    // ADVANCEMENTS
    AdvancementRegistry

    // TABLIST
    TablistFeature(server).startJob()
}