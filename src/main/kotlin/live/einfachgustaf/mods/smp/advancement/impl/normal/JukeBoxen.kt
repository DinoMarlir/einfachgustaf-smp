package live.einfachgustaf.mods.smp.advancement.impl.normal

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object JukeBoxen {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Blocks.JUKEBOX.asItem().defaultInstance,
                literalText("Juke-Boxen"),
                literalText("Schlage einen anderen Spieler mit einer Jukebox."),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(
                    Items.JUKEBOX.defaultInstance,
                )
            ),
            "jukeboxen",
            beginnerRoot,
            x = 2 * 1.5f,
            y = 3 * 1.5f
        )

        AttackEntityCallback.EVENT.register { player, _, hand, entity, _ ->
            if (entity is Player && !player.isSpectator && player.getItemInHand(hand).item == Blocks.JUKEBOX.asItem()) {

                player.asServerPlayer()?.let { serverPlayer ->
                    mcCoroutineScope.launch {
                        Advancements.awardAdvancement(serverPlayer, advancement)
                    }
                }
            }

            InteractionResult.PASS
        }
    }
}
