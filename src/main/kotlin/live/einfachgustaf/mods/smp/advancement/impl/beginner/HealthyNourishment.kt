package live.einfachgustaf.mods.smp.advancement.impl.beginner

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.PlayerEatEvent
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import me.obsilabor.alert.kotlin.subscribeToEvent
import net.minecraft.advancements.AdvancementType
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object HealthyNourishment {

    private val foodList: Array<Item> = arrayOf(
        Items.BREAD,
        Items.POTATO,
        Items.BAKED_POTATO,
        Items.CARROT,
        Items.APPLE,
        Items.SWEET_BERRIES,
        Items.KELP,
        Items.DRIED_KELP,
        Items.MUSHROOM_STEW,
        Items.SUSPICIOUS_STEW,
        Items.CHORUS_FRUIT,
        Items.GOLDEN_APPLE,
        Items.ENCHANTED_GOLDEN_APPLE,
        Items.MELON_SLICE,
        Items.GOLDEN_CARROT,
        Items.POISONOUS_POTATO,
        Items.BEETROOT,
        Items.BEETROOT_SOUP,
        Items.GLOW_BERRIES
    )

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.BREAD.defaultInstance,
                literalText("Healthy Nourishment"),
                literalText("Iss ein vegetarisches Lebensmittel."),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(
                    Items.STONE_HOE.defaultInstance,
                )
            ),
            "healthynourishment",
            beginnerRoot,
            x = 1.5f * 3,
        )

        subscribeToEvent<PlayerEatEvent> {
            if (foodList.contains(it.itemStack.item)) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(it.player.asServerPlayer() ?: return@launch, advancement)
                }
            }
        }
    }
}