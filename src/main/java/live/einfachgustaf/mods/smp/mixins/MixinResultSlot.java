package live.einfachgustaf.mods.smp.mixins;

import live.einfachgustaf.mods.smp.event.PlayerCraftItemEvent;
import me.obsilabor.alert.EventManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResultSlot.class)
public class MixinResultSlot {

    @Inject(at = @At("HEAD"), method = "onTake")
    public void injectSlotsChanged(Player player, ItemStack itemStack, CallbackInfo ci) {
        EventManager.callEvent(new PlayerCraftItemEvent(player, itemStack));
    }
}
