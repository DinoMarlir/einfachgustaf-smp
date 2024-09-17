package live.einfachgustaf.mods.smp.advancement.impl.challenging

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.PlayerCollectItemEvent
import live.einfachgustaf.mods.smp.event.PlayerCraftItemEvent
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import me.obsilabor.alert.kotlin.subscribeToEvent
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.silkmc.silk.core.item.itemStack
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

/**
 * Handles the "Colorful" advancement, which is awarded when a player has wool of every color in their inventory.
 */
object Colorful {

    private val woolSet = setOf(
        Items.WHITE_WOOL,
        Items.ORANGE_WOOL,
        Items.MAGENTA_WOOL,
        Items.LIGHT_BLUE_WOOL,
        Items.YELLOW_WOOL,
        Items.LIME_WOOL,
        Items.PINK_WOOL,
        Items.GRAY_WOOL,
        Items.LIGHT_GRAY_WOOL,
        Items.CYAN_WOOL,
        Items.PURPLE_WOOL,
        Items.BLUE_WOOL,
        Items.BROWN_WOOL,
        Items.GREEN_WOOL,
        Items.RED_WOOL,
        Items.BLACK_WOOL
    )

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.PINK_WOOL.defaultInstance,
                literalText("Colorful"),
                literalText("Habe Wolle in jeder Farbe im Inventar."),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(itemStack(Items.WHITE_WOOL, 64) {})
            ),
            "colorful",
            beginnerRoot,
            x = 1.5f * 5,
            y = 2 * 1.5f
        )

        subscribeToEvent<PlayerCraftItemEvent> { event ->
            if (hasWool(event.player)) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(event.player.asServerPlayer() ?: return@launch, advancement)
                }
            }
        }

        subscribeToEvent<PlayerCollectItemEvent> { event ->
            if (hasWool(event.player)) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(event.player.asServerPlayer() ?: return@launch, advancement)
                }
            }
        }
    }

    private fun hasWool(player: Player): Boolean {
        return woolSet.all { wool -> return player.inventory.items.any { it.item == wool } }
    }
}