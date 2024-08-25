package live.einfachgustaf.mods.smp.advancement

import live.einfachgustaf.mods.smp.advancement.impl.beginner.ALittleGift
import live.einfachgustaf.mods.smp.advancement.impl.beginner.GettingWood
import live.einfachgustaf.mods.smp.advancement.impl.beginner.TheBeginning
import live.einfachgustaf.mods.smp.advancement.impl.beginner.WhatTimeIsIt
import live.einfachgustaf.mods.smp.advancement.impl.normal.JukeBoxen

object AdvancementRegistry {

    init {
        TheBeginning.register()
        ALittleGift.register()
        WhatTimeIsIt.register()
        GettingWood.register()
        JukeBoxen.register()
    }
}
