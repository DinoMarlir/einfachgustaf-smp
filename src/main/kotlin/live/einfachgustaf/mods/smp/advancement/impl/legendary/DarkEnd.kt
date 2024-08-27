package live.einfachgustaf.mods.smp.advancement.impl.legendary

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import live.einfachgustaf.mods.smp.extensions.wasKilledByPlayer
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object DarkEnd {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.WARDEN_SPAWN_EGG.defaultInstance,
                literalText("Dunkles Ende"),
                literalText("Töte den Warden im End."),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf()
            ),
            "darkend",
            beginnerRoot,
            x = 1.5f,
            y = 4 * 1.5f
        )

        ServerLivingEntityEvents.AFTER_DEATH.register { entity, _ ->
            if (entity.type == EntityType.WARDEN
                && entity.wasKilledByPlayer()
                && entity.level().dimension() == Level.END
            ) {
                mcCoroutineScope.launch {
                    val serverPlayer = (entity.killCredit as Player).asServerPlayer() ?: return@launch

                    serverPlayer.giveExperienceLevels(70)
                    Advancements.awardAdvancement(serverPlayer, advancement)
                }
            }
        }
    }

}