package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.damagesource.DamageSource

/**
 * Event triggered when a player takes damage.
 *
 * @property player The player who took damage.
 * @property damage The amount of damage taken.
 * @property damageSource The source of the damage.
 */
class PlayerDamageEvent(
    val player: ServerPlayer,
    val damage: Float,
    val damageSource: DamageSource
) : Event()