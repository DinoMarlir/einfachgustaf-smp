package live.einfachgustaf.mods.smp.advancement.impl.normal

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.silkmc.silk.core.item.itemStack
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
            x = 1.5f
        )

        AttackEntityCallback.EVENT.register { player, world, hand, entity, hitResult ->
            if (entity is Player && player.getItemInHand(hand).item == Blocks.JUKEBOX.asItem()) {

                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(player, advancement)
                }
            }

            ActionResult.PASS
        }
    }
}
