package live.einfachgustaf.mods.smp.mixins;

import live.einfachgustaf.mods.smp.event.PlayerCollectItemEvent;
import me.obsilabor.alert.EventManager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public class MixinInventory {

    @Shadow
    @Final
    public Player player;

    @Inject(at = @At("HEAD"), method = "add(Lnet/minecraft/world/item/ItemStack;)Z")
    public void injectAdd(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        EventManager.callEvent(new PlayerCollectItemEvent(player, itemStack));
    }
}
