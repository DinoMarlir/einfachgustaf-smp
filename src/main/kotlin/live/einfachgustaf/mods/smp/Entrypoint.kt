package live.einfachgustaf.mods.smp

import com.mojang.brigadier.arguments.StringArgumentType
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.item.ItemArgument
import net.minecraft.commands.arguments.item.ItemInput
import net.minecraft.data.registries.VanillaRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerGamePacketListenerImpl
import net.minecraft.world.item.Items
import net.silkmc.silk.commands.command
import net.silkmc.silk.core.annotations.ExperimentalSilkApi
import net.silkmc.silk.core.text.literalText
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val LOGGER: Logger = LogManager.getLogger("smp")
    
fun initMain() {
}

@OptIn(ExperimentalSilkApi::class)
fun initServer() {
    // ADVANCEMENTS
    val root = Advancements.register(
        GustafAdvancement(Items.HONEYCOMB.defaultInstance, literalText("Bee welcome!"), literalText("We're glad to have you here!"), AdvancementType.TASK),
        "welcome",
        null
    )
    Advancements.register(
        GustafAdvancement(Items.OAK_BOAT.defaultInstance, literalText("Say hello!"), literalText("Introduce yourself in the chat!"), AdvancementType.GOAL),
        "welcome/hello",
        root,
        x = 3f
    )
    // COMMANDS
    val context = Commands.createValidationContext(VanillaRegistries.createLookup())
    command("notify") {
        requiresPermissionLevel(1)
        argument<ItemInput>("item", ItemArgument.item(context)) { item ->
            argument<String>("text", StringArgumentType.greedyString()) { text ->
                runs {
                    source.playerOrException.sendNotifcation(literalText(text.invoke(this)), item.invoke(this).createItemStack(1, false))
                }
            }
        }
    }
    command("gustaf-advancements") {
        requiresPermissionLevel(1)
        argument<String>("advancement", StringArgumentType.greedyString()) { advancement ->
            suggestList { Advancements.advancements().map { it.id.toString() } }
            runs {
                Advancements.awardAdvancement(source.playerOrException, Advancements.advancement(ResourceLocation(advancement.invoke(this)))!!)
            }
        }
    }
    // EVENTS
    ServerPlayConnectionEvents.JOIN.register { player, _, _ ->
        Advancements.createAdvancements(player.player) //TODO gets called twice?
    }
}