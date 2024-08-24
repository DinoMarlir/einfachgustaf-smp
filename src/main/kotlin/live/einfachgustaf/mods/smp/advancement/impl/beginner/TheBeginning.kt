package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Items
import net.silkmc.silk.core.item.itemStack
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object TheBeginning {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.HONEYCOMB.defaultInstance,
                literalText("Willkommen!"),
                literalText("Es freut uns, dass du hier bist!!"),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(
                    Items.STONE_SWORD.defaultInstance,
                    itemStack(Items.COOKED_BEEF) {
                        count = 16
                    }
                )
            ),
            "thebeginning",
            beginnerRoot,
            x = 1.5f
        )

        ServerPlayConnectionEvents.JOIN.register { event, _, _ ->
            mcCoroutineScope.launch {
                Advancements.awardAdvancement(event.player, advancement)
            }
        }
    }
}