package live.einfachgustaf.mods.smp.advancement.impl.challenging

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.advancements.AdvancementType
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Blocks
import net.silkmc.silk.core.Silk
import net.silkmc.silk.core.item.itemStack
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

/**
 * Handles the "Not Silky Enough" advancement, which is awarded when a player attempts to break a spawner with Silk Touch.
 */
object NotSilkyEnough {

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.DIAMOND_PICKAXE.defaultInstance,
                literalText("Not silky enough"),
                literalText("Versuche einen Spawner mit Silk Touch abzubauen"),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(
                    itemStack(Items.DIAMOND_PICKAXE) {
                        count = 1

                        val enchantmentRegistry =
                            Silk.serverOrThrow.registryAccess().registry(Registries.ENCHANTMENT).get()
                        enchant(enchantmentRegistry.getHolderOrThrow(Enchantments.SILK_TOUCH), 1)
                    }
                )
            ),
            "notsilkyenough",
            beginnerRoot,
            x = 1.5f,
            y = 2 * 1.5f
        )

        PlayerBlockBreakEvents.AFTER.register { _, player, _, itemStack, _ ->
            val itemHasSilkTouch = EnchantmentHelper.getEnchantmentsForCrafting(player.mainHandItem).keySet()
                .any { it.`is`(Enchantments.SILK_TOUCH) }
            if (itemStack == Blocks.SPAWNER && itemHasSilkTouch) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(player.asServerPlayer() ?: return@launch, advancement)
                }
            }
        }
    }
}