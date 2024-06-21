package live.einfachgustaf.mods.smp.advancement

import live.einfachgustaf.mods.smp.advancement.annontations.Advancement
import net.minecraft.world.entity.player.Player
import kotlin.reflect.full.findAnnotation

abstract class AbstractAdvancement {

    fun getInfo(): Advancement? {
        return this::class.findAnnotation<Advancement>()
    }

    open fun reward(player: Player) {  }
}