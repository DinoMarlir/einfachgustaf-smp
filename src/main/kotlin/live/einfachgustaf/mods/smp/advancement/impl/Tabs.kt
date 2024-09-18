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
        Items.HONEYCOMB.defaultInstance,
        literalText("Beginner"),
        literalText("Anfänger Advancements"),
        AdvancementType.TASK,
        ResourceLocation.withDefaultNamespace("textures/block/melon_side.png"),
        isUnlocked = true
    ),
    path = "beginner"
)

val normalRoot = Advancements.createTab(
    GustafAdvancement(
        Items.CAMPFIRE.defaultInstance,
        literalText("Normal"),
        literalText("Normale Advancements"),
        AdvancementType.TASK,
        ResourceLocation.withDefaultNamespace("textures/block/melon_side.png"),
        isUnlocked = true
    ),
    path = "normal"
)

val challengingRoot = Advancements.createTab(
    GustafAdvancement(
        Items.BOW.defaultInstance,
        literalText("Challenging"),
        literalText("Anspruchsvolle Advancements"),
        AdvancementType.TASK,
        ResourceLocation.withDefaultNamespace("textures/block/melon_side.png"),
        isUnlocked = true
    ),
    path = "challenging"
)

val legendaryRoot = Advancements.createTab(
    GustafAdvancement(
        Items.RECOVERY_COMPASS.defaultInstance,
        literalText("Legendary"),
        literalText("Schwere Advancements"),
        AdvancementType.TASK,
        ResourceLocation.withDefaultNamespace("textures/block/melon_side.png"),
        isUnlocked = true
    ),
    path = "legendary"
)