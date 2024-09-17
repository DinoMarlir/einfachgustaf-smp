package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

/**
 * Event triggered when a player eats food.
 *
 * @property player The player who ate the food.
 * @property level The level in which the player is.
 * @property itemStack The item stack that was eaten.
 * @property foodProperties The properties of the food that was eaten.
 */
class PlayerEatEvent(
    val player: Player,
    val level: Level,
    val itemStack: ItemStack,
    val foodProperties: FoodProperties
) : Event()