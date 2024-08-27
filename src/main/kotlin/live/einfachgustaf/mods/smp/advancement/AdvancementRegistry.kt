package live.einfachgustaf.mods.smp.advancement

import live.einfachgustaf.mods.smp.advancement.impl.beginner.*
import live.einfachgustaf.mods.smp.advancement.impl.challenging.AreYouUndead
import live.einfachgustaf.mods.smp.advancement.impl.normal.*

object AdvancementRegistry {

    init {
        TheBeginning.register()
        ALittleGift.register()
        WhatTimeIsIt.register()
        GettingWood.register()
        ATrueGustaf.register()
        JukeBoxen.register()
        AreYouUndead.register()
        NotSilkyEnough.register()
        DoABarrelroll.register()
        MasterOfExplosion.register()
        MoreSmoke.register()
        VillageAssassin.register()
        BirthdayParty.register()
    }
}
