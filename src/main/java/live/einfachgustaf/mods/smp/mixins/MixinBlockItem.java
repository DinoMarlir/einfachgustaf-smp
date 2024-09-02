package live.einfachgustaf.mods.smp.mixins;

import live.einfachgustaf.mods.smp.event.PlayerBlockPlaceEvent;
import me.obsilabor.alert.EventManager;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(BlockItem.class)
public class MixinBlockItem {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/InteractionResult;sidedSuccess(Z)Lnet/minecraft/world/InteractionResult;"), method = "place(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/InteractionResult;")
    public void injectPlace(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {
        EventManager.callEvent(new PlayerBlockPlaceEvent(Objects.requireNonNull(context.getPlayer()), (BlockItem) (Object) this, context));
    }

}
