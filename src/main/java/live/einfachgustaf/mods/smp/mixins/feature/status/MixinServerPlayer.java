package live.einfachgustaf.mods.smp.mixins.feature.status;

import live.einfachgustaf.mods.smp.Entrypoint;
import live.einfachgustaf.mods.smp.features.StatusFeature;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer {

    @Inject(at = @At("RETURN"), method = "getTabListDisplayName", cancellable = true)
    private void getTabListDisplayName(CallbackInfoReturnable<Component> cir) {
        StatusFeature.INSTANCE.injectTablist((ServerPlayer) (Object) this, cir);
    }
}
