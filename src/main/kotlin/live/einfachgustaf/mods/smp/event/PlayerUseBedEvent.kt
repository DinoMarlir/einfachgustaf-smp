package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.BedBlock

class PlayerUseBedEvent(
    val player: Player,
    val bedBlock: BedBlock
) : Event()