package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack

class PlayerDropItemEvent(
    val player: ServerPlayer,
    val item: ItemStack
): Event()