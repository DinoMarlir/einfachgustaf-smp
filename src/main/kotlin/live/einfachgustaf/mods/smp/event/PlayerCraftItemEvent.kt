package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

class PlayerCraftItemEvent(
    val player: Player,
    val item: ItemStack
): Event()