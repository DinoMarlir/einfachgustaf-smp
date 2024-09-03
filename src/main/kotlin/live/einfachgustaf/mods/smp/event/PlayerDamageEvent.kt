package live.einfachgustaf.mods.smp.event

import me.obsilabor.alert.Event
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.damagesource.DamageSource

class PlayerDamageEvent(
    val player: ServerPlayer,
    val damage: Float,
    val damageSource: DamageSource
) : Event()