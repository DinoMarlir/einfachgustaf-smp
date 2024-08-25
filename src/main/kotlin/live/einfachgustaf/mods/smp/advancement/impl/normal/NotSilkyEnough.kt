package live.einfachgustaf.mods.smp.advancement.impl.normal

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.BreakBlockEvent
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import me.obsilabor.alert.kotlin.subscribeToEvent
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

        subscribeToEvent<BreakBlockEvent> { event ->
            val itemHasSilkTouch = EnchantmentHelper.getEnchantmentsForCrafting(event.player.mainHandItem).keySet()
                .any { it.`is`(Enchantments.SILK_TOUCH) }
            if (event.block == Blocks.SPAWNER && itemHasSilkTouch) {
                mcCoroutineScope.launch {
                    Advancements.awardAdvancement(event.player.asServerPlayer() ?: return@launch, advancement)
                }
            }
        }
    }

}