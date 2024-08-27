package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState

class CandleLightUpEvent(
    val player: Player,
    val itemStack: ItemStack,
    val blockState: BlockState,
    val blockPos: BlockPos
) : Event()