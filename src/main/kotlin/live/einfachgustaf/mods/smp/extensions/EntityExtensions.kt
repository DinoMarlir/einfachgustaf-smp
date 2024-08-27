package live.einfachgustaf.mods.smp.extensions

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player

fun LivingEntity.wasKilledByPlayer(): Boolean {
    return this.killCredit != null && this.killCredit!! is Player
}