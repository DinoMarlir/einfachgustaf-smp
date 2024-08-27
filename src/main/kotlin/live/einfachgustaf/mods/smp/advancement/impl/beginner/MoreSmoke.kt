package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.PlayerBlockPlaceEvent
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import me.obsilabor.alert.kotlin.subscribeToEvent
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.silkmc.silk.core.entity.world
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object MoreSmoke {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.CAMPFIRE.defaultInstance,
                literalText("More Smoke"),
                literalText("Platziere einen Heuballen unter einem Lagerfeuer für eine größere Rauchseule."),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(Items.WATER_BUCKET.defaultInstance)
            ),
            "moresmoke",
            beginnerRoot,
            x = 1.5f * 2,
            y = 1.5f * 2
        )

        subscribeToEvent<PlayerBlockPlaceEvent> { event ->
            if (event.blockItem.block.asItem() == Items.CAMPFIRE
                && isBlockAtModifiedY(-1, Items.HAY_BLOCK, event)
            ) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(event.player.asServerPlayer() ?: return@launch, advancement)
                }
            }

            if (event.blockItem.block.asItem() == Items.HAY_BLOCK
                && isBlockAtModifiedY(1, Items.CAMPFIRE, event)
            ) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(event.player.asServerPlayer() ?: return@launch, advancement)
                }
            }
        }
    }

    private fun isBlockAtModifiedY(yModifier: Int, item: Item, event: PlayerBlockPlaceEvent): Boolean {
        return event.player.world.getBlockState(event.blockPlaceContext.clickedPos.atY(event.blockPlaceContext.clickedPos.y + yModifier)).block.asItem() == item
    }

}