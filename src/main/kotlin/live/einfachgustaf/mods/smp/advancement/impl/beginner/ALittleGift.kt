package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.PlayerCollectItemEvent
import live.einfachgustaf.mods.smp.event.PlayerDropItemEvent
import me.obsilabor.alert.kotlin.subscribeToEvent
import net.minecraft.advancements.AdvancementType
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.silkmc.silk.core.text.literalText
import net.silkmc.silk.core.task.mcCoroutineScope

object ALittleGift {
    val itemPlayerMap = mutableMapOf<ItemStack, ServerPlayer>()

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.HONEYCOMB.defaultInstance,
                literalText("Ein kleines Geschenk"),
                literalText("Gib einem anderen Spieler ein Geschenk"),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(Items.GOLD_INGOT.defaultInstance)
            ),
            "alittlegift",
            beginnerRoot,
            x = 1.5f * 2
        )

        subscribeToEvent<PlayerDropItemEvent> {
            itemPlayerMap[it.item] = it.player
        }

        subscribeToEvent<PlayerCollectItemEvent> {
            val player = itemPlayerMap[it.item]

            if (player != null && player != it.player) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(player, advancement)
                }
                itemPlayerMap.remove(it.item)
            }
        }
    }
}