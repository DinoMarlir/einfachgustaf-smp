package live.einfachgustaf.mods.smp.advancement.impl.challenging

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.PlayerDamageEvent
import me.obsilabor.alert.kotlin.subscribeToEvent
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Items
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

/**
 * Handles the "Are You Undead" advancement, which is awarded when a player takes 20 hearts of damage at once and survives.
 */
object AreYouUndead {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.SPIDER_SPAWN_EGG.defaultInstance,
                literalText("Bist du untot!"),
                literalText("Nehme 20 ganze Herzen Schaden auf einmal und überlebe es"),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(Items.GOLDEN_APPLE.defaultInstance)
            ),
            "areyouundead",
            beginnerRoot,
            x = 1.5f * 5
        )

        subscribeToEvent<PlayerDamageEvent> {
            if (it.damage >= 20.0f) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(it.player, advancement)
                }
            }
        }
    }
}