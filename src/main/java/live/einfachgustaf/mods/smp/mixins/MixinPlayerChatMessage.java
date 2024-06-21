package live.einfachgustaf.mods.smp.mixins;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerChatMessage.class)
public class MixinPlayerChatMessage {

    @Inject(at = @At("RETURN"), method = "decoratedContent", cancellable = true)
    private void injectDecoratedContent(CallbackInfoReturnable<Component> cir) {
        live.einfachgustaf.mods.smp.mixinkt.MixinPlayerChatMessage.INSTANCE.injectDecoratedContent(cir);
    }
}
