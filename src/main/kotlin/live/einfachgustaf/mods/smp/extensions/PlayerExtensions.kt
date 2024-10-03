package live.einfachgustaf.mods.smp.extensions

import live.einfachgustaf.mods.smp.audiences
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

/**
 * Extension function to get the audience of a player.
 *
 * @return the audience of the player.
 */
fun Player.audience() = audiences.player(uuid)