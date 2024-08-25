package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import net.fabricmc.fabric.api.event.player.PlayerCraftsItemCallback
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.silkmc.silk.core.item.itemStack
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object ATrueGustaf {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Blocks.JUKEBOX.asItem().defaultInstance,
                literalText("Ein wahrer Gustaf"),
                literalText("Crafte ein Jukebox."),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(
                    Items.DIAMOND.defaultInstance,
                )
            ),
            "atruegustaf",
            beginnerRoot,
            x = 1.5f
        )

        // Ereignis-Listener für das Crafting-Event
        PlayerCraftsItemCallback.EVENT.register { player, craftedStack, craftingInventory ->
            // Prüfe, ob das gecraftete Item eine Jukebox ist
            if (craftedStack.item == Blocks.JUKEBOX.asItem()) {

                // Vergib das Advancement
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(player, advancement)
                }
            }

            // Rückgabewert für das Event (standardmäßig KEIN Problem, um andere Event-Listener nicht zu stören)
            ActionResult.PASS
        }
    }
}
