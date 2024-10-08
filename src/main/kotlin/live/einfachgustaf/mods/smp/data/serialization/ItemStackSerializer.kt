package live.einfachgustaf.mods.smp.data.serialization

import com.mojang.brigadier.StringReader
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minecraft.commands.arguments.item.ItemInput
import net.minecraft.commands.arguments.item.ItemParser
import net.minecraft.core.Holder
import net.minecraft.data.registries.VanillaRegistries

import net.minecraft.world.item.ItemStack
import net.silkmc.silk.core.serialization.SilkSerializer

/**
 * Serializer for ItemStack.
 */
class ItemStackSerializer : SilkSerializer<ItemStack>() {
    private val context = VanillaRegistries.createLookup()
    private val itemParser = ItemParser(context)

    override fun deserialize(decoder: Decoder): ItemStack {
        val itemResult = itemParser.parse(StringReader(decoder.decodeString()))
        val itemInput = ItemInput(itemResult.item(), itemResult.components())
        return itemInput.createItemStack(1, false)
    }

    override fun serialize(encoder: Encoder, value: ItemStack) {
        val itemInput = ItemInput(Holder.direct(value.item), value.componentsPatch)
        encoder.encodeString(itemInput.serialize(context))
    }
}
