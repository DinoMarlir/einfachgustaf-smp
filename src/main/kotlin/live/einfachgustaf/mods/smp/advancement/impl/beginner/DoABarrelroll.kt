package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.PlayerCraftItemEvent
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import me.obsilabor.alert.kotlin.subscribeToEvent
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Items
import net.silkmc.silk.core.item.itemStack
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object DoABarrelroll {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.BARREL.defaultInstance,
                literalText("Do a Barrel Roll"),
                literalText("Crafte ein Fass"),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(itemStack(Items.OAK_LOG, 4) {})
            ),
            "doabarrelroll",
            beginnerRoot,
            x = 1.5f * 3,
            y = 1.5f
        )

        subscribeToEvent<PlayerCraftItemEvent> { event ->
            if (event.item.`is`(Items.BARREL)) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(event.player.asServerPlayer() ?: return@launch, advancement)
                }
            }
        }
    }

}