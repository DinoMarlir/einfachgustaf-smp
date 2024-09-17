package live.einfachgustaf.mods.smp.data.serialization

import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minecraft.data.registries.VanillaRegistries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

import net.silkmc.silk.core.serialization.SilkSerializer

/**
 * Serializer for MutableComponent.
 */
class ComponentSerializer : SilkSerializer<MutableComponent>() {
    override fun deserialize(decoder: Decoder): MutableComponent {
        return Component.Serializer.fromJson(decoder.decodeString(), VanillaRegistries.createLookup())!!
    }

    override fun serialize(encoder: Encoder, value: MutableComponent) {
        encoder.encodeString(Component.Serializer.toJson(value, VanillaRegistries.createLookup()))
    }
}
