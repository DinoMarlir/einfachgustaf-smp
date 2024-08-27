package live.einfachgustaf.mods.smp.advancement

import live.einfachgustaf.mods.smp.advancement.impl.beginner.*
import live.einfachgustaf.mods.smp.advancement.impl.challenging.AreYouUndead
import live.einfachgustaf.mods.smp.advancement.impl.challenging.MasterOfExplosion
import live.einfachgustaf.mods.smp.advancement.impl.challenging.NotSilkyEnough
import live.einfachgustaf.mods.smp.advancement.impl.legendary.DarkEnd
import live.einfachgustaf.mods.smp.advancement.impl.normal.ATrueGustaf
import live.einfachgustaf.mods.smp.advancement.impl.normal.JukeBoxen
import live.einfachgustaf.mods.smp.advancement.impl.normal.VillageAssassin

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
        DarkEnd.register()
        WillItExplode.register()
    }
}
