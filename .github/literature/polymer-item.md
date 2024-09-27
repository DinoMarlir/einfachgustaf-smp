```kotlin
package live.einfachgustaf.mods.smp

import eu.pb4.polymer.core.api.item.PolymerItem
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.silkmc.silk.core.text.sendText

class TestItem: PolymerItem, Item(Properties()) {

    override fun getPolymerItem(itemStack: ItemStack?, player: ServerPlayer?): Item {
        return Items.PAPER
    }

    override fun use(
        level: Level,
        player: Player,
        interactionHand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        player.sendText("you clicked!")
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand))
    }

    override fun releaseUsing(itemStack: ItemStack, level: Level, livingEntity: LivingEntity, i: Int) {
        livingEntity.sendText("you released!")
    }

    companion object {
        fun register() {
            Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath("smp", "test_item"), TestItem())
        }
    }
}
```