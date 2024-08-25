package live.einfachgustaf.mods.smp.advancement.impl.normal

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.PlayerCraftItemEvent
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import me.obsilabor.alert.kotlin.subscribeToEvent
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
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

        subscribeToEvent<PlayerCraftItemEvent> {
            if (it.item == Blocks.JUKEBOX.asItem().defaultInstance) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(it.player.asServerPlayer() ?: return@launch, advancement)
                }
            }
        }
    }
}
