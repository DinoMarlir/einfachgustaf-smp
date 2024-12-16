package live.einfachgustaf.mods.smp.advancement

import net.minecraft.advancements.AdvancementType
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

inline fun advancement(
    displayIcon: ItemStack,
    title: MutableComponent,
    description: MutableComponent,
    type: AdvancementType,
    backgroundResource: ResourceLocation = Advancements.DEFAULT_RESOURCE,
    unlocks: Set<ResourceLocation> = setOf(),
    isUnlocked: Boolean = false,
    rewards: Set<ItemStack> = setOf(),

    path: String,
    parent: CompilableAdvancement? = null,
    x: Float = 0f,
    y: Float = 0f,

    register: Boolean = false,
    onRegister: (GustafAdvancement) -> Unit
): GustafAdvancement {
    val advancement = GustafAdvancement(
        displayIcon,
        title,
        description,
        type,
        backgroundResource,
        unlocks,
        isUnlocked,
        rewards
    )

    if (register) {
        Advancements.register(advancement, path, parent, x, y)
        onRegister.invoke(advancement)
    }

    return advancement
}