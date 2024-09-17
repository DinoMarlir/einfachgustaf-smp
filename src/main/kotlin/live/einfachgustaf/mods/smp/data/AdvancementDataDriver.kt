package live.einfachgustaf.mods.smp.data

import kotlinx.serialization.Serializable
import live.einfachgustaf.mods.smp.advancement.GustafAdvancement
import live.einfachgustaf.mods.smp.data.serialization.ItemStackSerializer
import net.minecraft.world.item.ItemStack

/**
 * Data class representing player advancement data.
 *
 * @property uuid The unique identifier of the player.
 * @property advancements The set of advancements the player has achieved.
 */
@Serializable
data class PlayerAdvancementData(
    val uuid: String,
    val advancements: Set<String>
)

/**
 * Data class representing the advancement data for a player.
 *
 * @property root The root advancement.
 * @property advancements The set of advancements held by the player.
 */
@Suppress("unused")
@Serializable
data class AdvancementData(
    val root: GustafAdvancement,
    val advancements: Set<AdvancementHolder>
)

/**
 * Data class representing an advancement holder.
 *
 * @property path The path of the advancement.
 * @property advancement The advancement itself.
 */
@Serializable
data class AdvancementHolder(
    val path: String,
    val advancement: GustafAdvancement
)

/**
 * Data class representing an item stack holder.
 * This could be used to serialize/deserialize item stacks.
 *
 * @property itemStack The item stack held by the player.
 */
@Serializable
data class ItemStackHolder(
    @Serializable(with = ItemStackSerializer::class) val itemStack: ItemStack
)