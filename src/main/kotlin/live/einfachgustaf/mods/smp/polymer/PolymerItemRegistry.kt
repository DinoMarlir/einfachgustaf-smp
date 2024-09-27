package live.einfachgustaf.mods.smp.polymer

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

/**
 * Registry for Polymer items
 */
object PolymerItemRegistry {

    /**
     * Items to register
     */
    private val items = mapOf<Item, ResourceLocation>(

    )

    /**
     * Register items
     */
    init {
        items.forEach {
            Registry.register(BuiltInRegistries.ITEM, it.value, it.key)
        }
    }
}