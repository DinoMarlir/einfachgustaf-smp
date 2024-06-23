package live.einfachgustaf.mods.smp

import com.mojang.brigadier.arguments.StringArgumentType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.data.AdvancementData
import live.einfachgustaf.mods.smp.data.AdvancementHolder
import live.einfachgustaf.mods.smp.data.ItemStackHolder
import live.einfachgustaf.mods.smp.data.serialization.ItemStackSerializer
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.item.ItemArgument
import net.minecraft.commands.arguments.item.ItemInput
import net.minecraft.data.registries.VanillaRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.silkmc.silk.commands.command
import net.silkmc.silk.core.text.literalText
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

val LOGGER: Logger = LogManager.getLogger("smp")
    
fun initMain() {
}

@OptIn(ExperimentalSerializationApi::class)
val JSON = Json {
    prettyPrint = true
    prettyPrintIndent = "  "
    isLenient = true
    ignoreUnknownKeys = true
}

fun initServer() {
    // ADVANCEMENTS
    val root = Advancements.createTab(
        GustafAdvancement(
            Items.WIND_CHARGE.defaultInstance,
            literalText("EinfachGustaf"),
            literalText("Test Advancements"),
            AdvancementType.TASK,
            ResourceLocation("textures/block/melon_side.png"),
            isUnlocked = true
        )
    )
    val welcome = Advancements.register(
        GustafAdvancement(
            Items.HONEYCOMB.defaultInstance,
            literalText("Bee welcome!"),
            literalText("We're glad to have you here!"),
            AdvancementType.TASK,
            isUnlocked = true,
            unlocks = setOf(Advancements.res("welcome/hello"), Advancements.res("welcome/claim"))
        ),
        "welcome",
        root,
        x = 1.5f,
    )
    val claim = Advancements.register(
        GustafAdvancement(Items.WHITE_BANNER.defaultInstance, literalText("Settler!"), literalText("Claim a chunk!"), AdvancementType.TASK, unlocks = setOf(Advancements.res("welcome/claim16"))),
        "welcome/claim",
        welcome,
        x = 3f,
        y = 0f,
    )
    val claim16 = Advancements.register(
        GustafAdvancement(Items.PINK_BANNER.defaultInstance, literalText("My Empire!"), literalText("Claim at least 16 chunks"), AdvancementType.CHALLENGE),
        "welcome/claim16",
        claim,
        x = 4f,
        y = 0f,
    )
    val hello = Advancements.register(
        GustafAdvancement(Items.OAK_BOAT.defaultInstance, literalText("Hello!"), literalText("Introduce yourself in the chat!"), AdvancementType.GOAL),
        "welcome/hello",
        welcome,
        x = 3f,
        y = 2f
    )
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
                Advancements.awardAdvancement(source.playerOrException, Advancements.advancement(ResourceLocation(advancement.invoke(this)))!!)
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