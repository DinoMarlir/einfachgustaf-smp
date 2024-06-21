package live.einfachgustaf.mods.smp.advancement

import net.minecraft.advancements.AdvancementType
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

data class GustafAdvancement(
    val displayIcon: ItemStack,
    val title: Component,
    val description: Component,
    val type: AdvancementType,
    val backgroundResource: ResourceLocation = ResourceLocation("textures/gui/advancements/backgrounds/adventure.png")
) {
}