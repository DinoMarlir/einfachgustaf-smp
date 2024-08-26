package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.context.BlockPlaceContext

class PlayerBlockPlaceEvent(
    val player: Player,
    val blockItem: BlockItem,
    val blockPlaceContext: BlockPlaceContext
) : Event()