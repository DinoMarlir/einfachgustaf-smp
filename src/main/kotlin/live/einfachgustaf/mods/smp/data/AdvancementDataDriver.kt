package live.einfachgustaf.mods.smp.data

import kotlinx.serialization.Serializable
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.data.serialization.ItemStackSerializer
import net.minecraft.world.item.ItemStack

@Serializable
data class AdvancementData(
    val root: GustafAdvancement
)

@Serializable
data class ItemStackHolder(
    @Serializable(with = ItemStackSerializer::class) val itemStack: ItemStack
)