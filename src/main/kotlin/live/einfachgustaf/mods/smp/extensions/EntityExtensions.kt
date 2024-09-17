package live.einfachgustaf.mods.smp.extensions

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player

/**
 * Extension function to check if a LivingEntity was killed by a Player.
 *
 * @return true if the LivingEntity was killed by a Player, false otherwise.
 */
fun LivingEntity.wasKilledByPlayer(): Boolean {
    return this.killCredit != null && this.killCredit!! is Player
}