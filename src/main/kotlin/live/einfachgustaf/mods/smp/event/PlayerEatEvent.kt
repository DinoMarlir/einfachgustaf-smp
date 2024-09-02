package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class PlayerEatEvent(
    val player: Player,
    val level: Level,
    val itemStack: ItemStack,
    val foodProperties: FoodProperties
): Event()