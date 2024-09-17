package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.context.BlockPlaceContext

/**
 * Event triggered when a player places a block.
 *
 * @property player The player who placed the block.
 * @property blockItem The block item that was placed.
 * @property blockPlaceContext The context in which the block was placed.
 */
class PlayerBlockPlaceEvent(
    val player: Player,
    val blockItem: BlockItem,
    val blockPlaceContext: BlockPlaceContext
) : Event()