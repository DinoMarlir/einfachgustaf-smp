package live.einfachgustaf.mods.smp.advancement

import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.DisplayInfo
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import java.util.*

object Advancements {

    val DEFAULT_RESOURCE = ResourceLocation("textures/gui/advancements/backgrounds/adventure.png")

    fun createAdvancementEntry(forAdvancement: GustafAdvancement): AdvancementHolder {
        val entry = Advancement.Builder.advancement()
            .display(DisplayInfo(
                forAdvancement.displayIcon,
                forAdvancement.title,
                forAdvancement.description,
                Optional.of(forAdvancement.backgroundResource),
                forAdvancement.type,
                true,
                false,
                false
            ))
            .build(ResourceLocation("einfachgustaf:/root"))
        return entry
    }

    fun ServerPlayer.awardAdvancement(advancement: AdvancementHolder) {
        advancements.award(advancement, "dummy")
    }
}