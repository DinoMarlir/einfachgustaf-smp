package live.einfachgustaf.mods.smp.advancement

import live.einfachgustaf.mods.smp.advancement.Advancements.location
import live.einfachgustaf.mods.smp.advancement.Advancements.res
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.DisplayInfo
import net.minecraft.advancements.critereon.ImpossibleTrigger
import net.minecraft.resources.ResourceLocation
import java.util.*

data class CompilableAdvancement(
    val gustafAdvancement: GustafAdvancement,
    val x: Float = 0f,
    val y: Float = 0f,
    val parent: ResourceLocation? = null,
    val path: String
) {

    /**
     * Compiles the advancement to an mojang AdvancementHolder
     */
    fun compile(hide: Boolean = false, showToast: Boolean = true, showInChat: Boolean = true): AdvancementHolder {
        val entry = Advancement.Builder.advancement()
            .display(DisplayInfo(
                gustafAdvancement.displayIcon,
                gustafAdvancement.title,
                gustafAdvancement.description,
                Optional.of(gustafAdvancement.backgroundResource),
                gustafAdvancement.type,
                showToast,
                showInChat,
                hide
            ).location(x, y))
            .addCriterion("dummy", CriteriaTriggers.IMPOSSIBLE.createCriterion(ImpossibleTrigger.TriggerInstance()))
        if (parent != null) {
            entry.parent(Advancements.advancement(parent)!!.compile())
        }
        return entry.build(res(path))
    }

    val id: ResourceLocation
        get() = res(path)

}
