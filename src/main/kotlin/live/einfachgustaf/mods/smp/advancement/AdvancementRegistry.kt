package live.einfachgustaf.mods.smp.advancement

import live.einfachgustaf.mods.smp.advancement.impl.beginner.ALittleGift
import live.einfachgustaf.mods.smp.advancement.impl.beginner.TheBeginning
import live.einfachgustaf.mods.smp.advancement.impl.challenging.AreYouUndead

object AdvancementRegistry {

    init {
        TheBeginning.register()
        ALittleGift.register()
        AreYouUndead.register()
    }
}