package live.einfachgustaf.mods.smp.advancement

import net.minecraft.advancements.Advancement
import net.minecraft.advancements.DisplayInfo
import net.minecraft.resources.ResourceLocation
import java.util.*

object Advancements {
    fun createAdvancementEntry(forAdvancement: GustafAdvancement) {
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
    }
}