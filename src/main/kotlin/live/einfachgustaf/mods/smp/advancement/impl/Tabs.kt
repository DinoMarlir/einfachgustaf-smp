package live.einfachgustaf.mods.smp.advancement.impl

import live.einfachgustaf.mods.smp.advancement.Advancements
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import net.minecraft.advancements.AdvancementType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.silkmc.silk.core.text.literalText

/**
 * The root advancement for the beginner category.
 * This advancement serves as the starting point for all beginner advancements.
 * It is unlocked by default.
 */
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