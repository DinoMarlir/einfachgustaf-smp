package live.einfachgustaf.mods.smp

import com.mojang.brigadier.arguments.StringArgumentType
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.data.ItemStackHolder
import live.einfachgustaf.mods.smp.data.db.MongoDB
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.item.ItemArgument
import net.minecraft.commands.arguments.item.ItemInput
import net.minecraft.data.registries.VanillaRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.silkmc.silk.commands.command
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val LOGGER: Logger = LogManager.getLogger("smp")
    
fun initMain() {
}

@Suppress("UNUSED_VARIABLE")
fun initServer() {
    // DATABASE
    MongoDB

    // COMMANDS
    val context = Commands.createValidationContext(VanillaRegistries.createLookup())
    command("notify") {
        requiresPermissionLevel(1)
        argument<ItemInput>("item", ItemArgument.item(context)) { item ->
            argument<String>("type") { type ->
                suggestList { listOf("TASK", "GOAL", "CHALLENGE") }
                argument<String>("text", StringArgumentType.greedyString()) { text ->
                    runs {
                        val remappedType = when(type.invoke(this)) {
                            "TASK" -> AdvancementType.TASK
                            "GOAL" -> AdvancementType.GOAL
                            "CHALLENGE" -> AdvancementType.CHALLENGE
                            else -> throw IllegalArgumentException()
                        }
                        source.playerOrException.sendNotifcation(literalText(text.invoke(this)), item.invoke(this).createItemStack(1, false), remappedType)
                    }
                }
            }
        }
    }
    command("gustaf-advancements") {
        requiresPermissionLevel(1)
        argument<String>("advancement", StringArgumentType.greedyString()) { advancement ->
            suggestList { Advancements.advancements().map { it.id.toString() } }
            runs {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(source.playerOrException, Advancements.advancement(ResourceLocation.parse(advancement.invoke(this@runs)))!!)
                }
            }
        }
    }
    command("serialize-item-test") {
        requiresPermissionLevel(1)
        runs {
            val item = ItemStackHolder(source.playerOrException.mainHandItem)
            source.sendSystemMessage(literalText(Json.encodeToString(item)))
        }
    }
    // EVENTS
    ServerPlayConnectionEvents.JOIN.register { player, _, _ ->
        Advancements.createAdvancements(player.player)

    }
}