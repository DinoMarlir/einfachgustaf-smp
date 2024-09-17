package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

/**
 * Event triggered when a player collects an item.
 *
 * @property player The player who collected the item.
 * @property item The item stack that was collected.
 */
class PlayerCollectItemEvent(
    val player: Player,
    val item: ItemStack
) : Event()