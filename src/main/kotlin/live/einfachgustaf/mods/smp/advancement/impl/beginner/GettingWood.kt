package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object GettingWood {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.IRON_AXE.defaultInstance,
                literalText("Getting Wood!"),
                literalText("Baue einen Holzblock ab."),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(
                    Items.WOODEN_AXE.defaultInstance,
                )
            ),
            "gettingwood",
            beginnerRoot,
            x = 1.5f
        )

        PlayerBlockBreakEvents.BEFORE.register { world, player, pos, state, blockEntity ->
            if (state.block == Blocks.OAK_LOG) {

                // Vergib das Advancement
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(player.asServerPlayer() ?: return@launch, advancement)
                }
            }
            true
        }
    }
}