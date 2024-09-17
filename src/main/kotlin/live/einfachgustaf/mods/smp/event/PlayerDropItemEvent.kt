package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

/**
 * Event triggered when a player drops an item.
 *
 * @property player The player who dropped the item.
 * @property item The item stack that was dropped.
 */
class PlayerDropItemEvent(
    val player: ServerPlayer,
    val item: ItemStack
) : Event()