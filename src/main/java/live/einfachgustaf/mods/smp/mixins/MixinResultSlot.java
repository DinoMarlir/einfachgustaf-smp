package live.einfachgustaf.mods.smp.mixins;

import live.einfachgustaf.mods.smp.event.PlayerCraftItemEvent;
import me.obsilabor.alert.EventManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResultSlot.class)
public class MixinResultSlot {

    @Shadow
    @Final
    private Player player;

    private boolean wasQuickCrafted = false;

    @Inject(at = @At("HEAD"), method = "onTake")
    public void injectSlotsChanged(Player player, ItemStack itemStack, CallbackInfo ci) {
        if (!wasQuickCrafted)
            EventManager.callEvent(new PlayerCraftItemEvent(player, itemStack));

        wasQuickCrafted = false;
    }

    @Inject(at = @At("HEAD"), method = "onQuickCraft(Lnet/minecraft/world/item/ItemStack;I)V")
    public void injectQuickCrafted(ItemStack itemStack, int i, CallbackInfo ci) {
        EventManager.callEvent(new PlayerCraftItemEvent(player, itemStack));
        wasQuickCrafted = true;
    }
}
