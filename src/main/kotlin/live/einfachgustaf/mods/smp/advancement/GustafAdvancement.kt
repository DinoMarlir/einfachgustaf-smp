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

/**
 * Data class representing an advancement in the game.
 *
 * @property displayIcon The icon representing the advancement.
 * @property title The title of the advancement.
 * @property description The description of the advancement.
 * @property type The type of the advancement (e.g., task, challenge).
 * @property backgroundResource The resource location of the background image for the advancement.
 * @property unlocks The set of advancements that are unlocked by this advancement.
 * @property isUnlocked Indicates whether the advancement is currently unlocked.
 * @property rewards The set of rewards associated with the advancement.
 */
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