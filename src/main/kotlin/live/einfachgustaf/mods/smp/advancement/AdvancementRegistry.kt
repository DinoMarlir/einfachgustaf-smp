package live.einfachgustaf.mods.smp.advancement

import live.einfachgustaf.mods.smp.advancement.impl.beginner.ALittleGift
import live.einfachgustaf.mods.smp.advancement.impl.beginner.GettingWood
import live.einfachgustaf.mods.smp.advancement.impl.beginner.TheBeginning
import live.einfachgustaf.mods.smp.advancement.impl.beginner.WhatTimeIsIt
import live.einfachgustaf.mods.smp.advancement.impl.challenging.AreYouUndead
import live.einfachgustaf.mods.smp.advancement.impl.normal.ATrueGustaf
import live.einfachgustaf.mods.smp.advancement.impl.normal.JukeBoxen

object AdvancementRegistry {

    init {
        TheBeginning.register()
        ALittleGift.register()
        WhatTimeIsIt.register()
        GettingWood.register()
        ATrueGustaf.register()
        JukeBoxen.register()
        AreYouUndead.register()
    }
}
