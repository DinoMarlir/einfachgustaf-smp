package live.einfachgustaf.mods.smp.extensions

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.silkmc.silk.core.Silk

fun Player.asServerPlayer(): ServerPlayer? {
    return Silk.server?.playerList?.getPlayer(this.uuid)
}