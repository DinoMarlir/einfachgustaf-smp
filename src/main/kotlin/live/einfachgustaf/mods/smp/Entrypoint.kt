package live.einfachgustaf.mods.smp

import com.mojang.brigadier.arguments.StringArgumentType
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import live.einfachgustaf.mods.smp.advancement.AdvancementRegistry
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.data.ItemStackHolder
import live.einfachgustaf.mods.smp.data.db.MongoDB
import live.einfachgustaf.mods.smp.discord.DiscordBot
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.item.ItemArgument
import net.minecraft.commands.arguments.item.ItemInput
import net.minecraft.data.registries.VanillaRegistries
import net.minecraft.resources.ResourceLocation
import net.silkmc.silk.commands.command
import net.silkmc.silk.core.annotations.ExperimentalSilkApi
import net.silkmc.silk.core.event.Events
import net.silkmc.silk.core.event.Server
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val LOGGER: Logger = LogManager.getLogger("smp")
    
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

    postStart()
}

@OptIn(ExperimentalSilkApi::class)
fun postStart() = Events.Server.postStart.listen {
    // ADVANCEMENTS
    AdvancementRegistry
}