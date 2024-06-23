package live.einfachgustaf.mods.smp.advancement

import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementHolder
import net.minecraft.resources.ResourceLocation

data class CompiledAdvancement(
    val gustafAdvancement: GustafAdvancement,
    val holder: AdvancementHolder
) {

    val id: ResourceLocation
        get() = holder.id

    val value: Advancement
        get() = holder.value

}
