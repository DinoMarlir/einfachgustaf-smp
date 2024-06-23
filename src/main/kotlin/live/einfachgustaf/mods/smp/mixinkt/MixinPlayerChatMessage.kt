package live.einfachgustaf.mods.smp.mixinkt

import net.minecraft.network.chat.Component
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

object MixinPlayerChatMessage {

    fun injectDecoratedContent(cir: CallbackInfoReturnable<Component>) {
        // TODO: MiniMessage Translation
    }
}