package live.einfachgustaf.mods.smp.advancement

import live.einfachgustaf.mods.smp.advancement.impl.beginner.ALittleGift
import live.einfachgustaf.mods.smp.advancement.impl.beginner.TheBeginning

object AdvancementRegistry {

    init {
        TheBeginning.register()
        ALittleGift.register()
        WhatTimeIsIt.register()
        GettingWood.register()
        JukeBoxen.register()
        
    }
}
