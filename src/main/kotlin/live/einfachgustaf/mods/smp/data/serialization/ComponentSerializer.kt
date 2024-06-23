package live.einfachgustaf.mods.smp.data.serialization

import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minecraft.commands.Commands
import net.minecraft.data.registries.VanillaRegistries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

import net.silkmc.silk.core.serialization.SilkSerializer

class ComponentSerializer : SilkSerializer<MutableComponent>() {
    private val context = Commands.createValidationContext(VanillaRegistries.createLookup())

    override fun deserialize(decoder: Decoder): MutableComponent {
        return Component.Serializer.fromJson(decoder.decodeString(), context)!!
    }

    override fun serialize(encoder: Encoder, value: MutableComponent) {
        encoder.encodeString(Component.Serializer.toJson(value, context))
    }
}
