package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.Block

class PlayerUseBlockEvent(
    val player: Player,
    val block: Block
) : Event()