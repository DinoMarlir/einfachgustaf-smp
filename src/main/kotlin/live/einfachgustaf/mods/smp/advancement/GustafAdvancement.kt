@file:UseSerializers(ResourceLocationSerializer::class, ItemStackSerializer::class, ComponentSerializer::class)
package live.einfachgustaf.mods.smp.advancement

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import live.einfachgustaf.mods.smp.data.serialization.ComponentSerializer
import live.einfachgustaf.mods.smp.data.serialization.ItemStackSerializer
import net.minecraft.advancements.AdvancementType
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.silkmc.silk.core.serialization.serializers.ResourceLocationSerializer

@Serializable
data class GustafAdvancement(
    val displayIcon: ItemStack,
    val title: MutableComponent,
    val description: MutableComponent,
    val type: AdvancementType,
    val backgroundResource: ResourceLocation = Advancements.DEFAULT_RESOURCE,
    val unlocks: Set<ResourceLocation> = setOf(),
    val isUnlocked: Boolean = false,
    val rewards: Set<ItemStack> = setOf()
)