package live.einfachgustaf.mods.smp.advancement.impl.normal

import kotlinx.coroutines.launch
import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.CompilableAdvancement
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.advancement.impl.beginnerRoot
import live.einfachgustaf.mods.smp.event.PlayerCollectItemEvent
import live.einfachgustaf.mods.smp.event.PlayerCraftItemEvent
import live.einfachgustaf.mods.smp.extensions.asServerPlayer
import me.obsilabor.alert.kotlin.subscribeToEvent
import net.minecraft.advancements.AdvancementType
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.silkmc.silk.core.Silk
import net.silkmc.silk.core.item.itemStack
import net.silkmc.silk.core.task.mcCoroutineScope
import net.silkmc.silk.core.text.literalText

object MasterOfExplosion {

    private val explosionItems = setOf(
        Items.TNT,
        Items.TNT_MINECART,
        Items.END_CRYSTAL,
        Items.RESPAWN_ANCHOR
    )

    private val bedItems = setOf(
        Items.BLUE_BED,
        Items.RED_BED,
        Items.GREEN_BED,
        Items.YELLOW_BED,
        Items.PURPLE_BED,
        Items.BLACK_BED,
        Items.WHITE_BED,
        Items.ORANGE_BED,
        Items.LIGHT_BLUE_BED,
        Items.LIGHT_GRAY_BED,
        Items.LIME_BED,
        Items.MAGENTA_BED,
        Items.BROWN_BED,
        Items.CYAN_BED,
        Items.GRAY_BED,
        Items.PINK_BED
    )

    fun register() {
        val advancement = Advancements.register(
            GustafAdvancement(
                Items.TNT.defaultInstance,
                literalText("Meister der Explosion"),
                literalText("Habe alle Items die Explodieren können zur selben Zeit im Inventar."),
                AdvancementType.TASK,
                isUnlocked = true,
                rewards = setOf(
                    itemStack(Items.ENCHANTED_BOOK) {
                        val enchantmentRegistry =
                            Silk.serverOrThrow.registryAccess().registry(Registries.ENCHANTMENT).get()
                        enchant(enchantmentRegistry.getHolderOrThrow(Enchantments.BLAST_PROTECTION), 4)
                    }
                )
            ),
            "masterofexplosion",
            beginnerRoot,
            x = 4 * 1.5f,
            y = 1.5f
        )

        subscribeToEvent<PlayerCraftItemEvent> { event ->
            checkInventory(event.player, event.item, advancement)
        }

        subscribeToEvent<PlayerCollectItemEvent> { event ->
            checkInventory(event.player, event.item, advancement)
        }
    }

    private fun checkInventory(player: Player, itemStack: ItemStack, advancement: CompilableAdvancement) {
        val hasBed =
            player.inventory.items.any { item -> bedItems.contains(item.item) } || bedItems.contains(itemStack.item)
        val hasAllExplosionItems =
            explosionItems.all { item -> player.inventory.items.any { it.item == item } || item == itemStack.item }

        if (hasBed && hasAllExplosionItems) {
            mcCoroutineScope.launch {
                Advancements.awardAdvancement(player.asServerPlayer() ?: return@launch, advancement)
            }
        }
    }

}