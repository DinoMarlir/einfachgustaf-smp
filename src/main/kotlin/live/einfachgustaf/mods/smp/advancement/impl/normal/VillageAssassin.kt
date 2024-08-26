package live.einfachgustaf.mods.smp.advancement.impl.normal

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.silkmc.silk.core.item.itemStack
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object VillageAssassin {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.IRON_SWORD.defaultInstance,
                literalText("Village Assassin"),
                literalText("Töte einen Villager"),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(itemStack(Items.EMERALD, 3) {})
            ),
            "villageassassin",
            beginnerRoot,
            x = 1.5f * 3,
            y = 1.5f * 2
        )

        ServerLivingEntityEvents.AFTER_DEATH.register { entity, _ ->
            if (entity.type == EntityType.VILLAGER && entity.killCredit != null && entity.killCredit!! is Player) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(
                        (entity.killCredit as Player).asServerPlayer() ?: return@launch,
                        advancement
                    )
                }
            }
        }
    }

}