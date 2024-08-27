package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.PlayerUseBlockEvent
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import me.obsilabor.alert.kotlin.subscribeToEvent
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.silkmc.silk.core.item.itemStack
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object WillItExplode {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.RED_BED.defaultInstance,
                literalText("Will it explode?"),
                literalText("Nutze ein Bett im Nether"),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(itemStack(Items.OBSIDIAN, 4) {})
            ),
            "willitexplode",
            beginnerRoot,
            x = 1.5f * 4,
            y = 1.5f * 3
        )

        subscribeToEvent<PlayerUseBlockEvent> { event ->
            if (event.player.level().dimension() == Level.NETHER) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(event.player.asServerPlayer() ?: return@launch, advancement)
                }
            }
        }
    }

}