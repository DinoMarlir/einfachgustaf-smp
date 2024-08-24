package live.einfachgustaf.mods.smp.advancement.impl

import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import net.minecraft.advancements.AdvancementType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.silkmc.silk.core.text.literalText

val beginnerRoot = Advancements.createTab(
    GustafAdvancement(
        Items.WIND_CHARGE.defaultInstance,
        literalText("EinfachGustaf"),
        literalText("Test Advancements"),
        AdvancementType.TASK,
        ResourceLocation.withDefaultNamespace("textures/block/melon_side.png"),
        isUnlocked = true
    )
)