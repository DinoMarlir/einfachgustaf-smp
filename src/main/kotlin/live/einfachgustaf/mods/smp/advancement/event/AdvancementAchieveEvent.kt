package live.einfachgustaf.mods.smp.advancement.event

import live.einfachgustaf.mods.smp.advancement.annontations.Advancement
import me.obsilabor.alert.Event
import net.minecraft.world.entity.player.Player

class AdvancementAchieveEvent(val advancement: Advancement, val player: Player): Event()