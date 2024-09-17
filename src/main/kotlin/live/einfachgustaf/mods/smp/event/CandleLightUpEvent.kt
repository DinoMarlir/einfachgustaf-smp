package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState

/**
 * Event triggered when a candle is lit up.
 *
 * @property player The player who lit the candle.
 * @property itemStack The item stack used to light the candle.
 * @property blockState The state of the block where the candle is located.
 * @property blockPos The position of the block where the candle is located.
 */
class CandleLightUpEvent(
    val player: Player,
    val itemStack: ItemStack,
    val blockState: BlockState,
    val blockPos: BlockPos
) : Event()