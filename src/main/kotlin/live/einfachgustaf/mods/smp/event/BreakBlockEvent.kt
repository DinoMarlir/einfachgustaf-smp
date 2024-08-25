package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity

class BreakBlockEvent(
    val player: Player,
    val block: Block,
    val blockEntity: BlockEntity,
    val level: Level,
    val itemStack: ItemStack
) : Event()