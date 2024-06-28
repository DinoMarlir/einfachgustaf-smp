package live.einfachgustaf.mods.smp.mixins.feature.status;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class MixinPlayer {

    @Inject(at = @At("RETURN"), method = "getName", cancellable = true)
    public void injectGetName(CallbackInfoReturnable<Component> cir) {
        cir.setReturnValue(Component.literal("Hallo"));
    }
}
