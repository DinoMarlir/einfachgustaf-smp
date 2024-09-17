package live.einfachgustaf.mods.smp.extensions

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.silkmc.silk.core.Silk

/**
 * Extension function to convert a Player to a ServerPlayer.
 *
 * @return the corresponding ServerPlayer if it exists, null otherwise.
 */
fun Player.asServerPlayer(): ServerPlayer? {
    return Silk.server?.playerList?.getPlayer(this.uuid)
}