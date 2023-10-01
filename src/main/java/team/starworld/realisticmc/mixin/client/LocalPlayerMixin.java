package team.starworld.realisticmc.mixin.client;

import earth.terrarium.ad_astra.common.item.FluidContainingItem;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.starworld.realisticmc.client.screen.PlayerOverlayScreen;
import team.starworld.realisticmc.content.item.armor.DivingGear;
import team.starworld.realisticmc.content.item.armor.HazmatGear;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void rmc$tick (CallbackInfo ci) {
        var player = (LocalPlayer) (Object) this;
        PlayerOverlayScreen.shouldRenderOxygen = DivingGear.hasFullGear(player) || HazmatGear.hasFullGear(player);
        if (PlayerOverlayScreen.shouldRenderOxygen) {
            var chest = player.getItemBySlot(EquipmentSlot.CHEST);
            var item = chest.getItem();
            if (item instanceof FluidContainingItem gear)
                PlayerOverlayScreen.oxygenRatio = Mth.clamp(gear.getFluidAmount(chest) / (double) gear.getTankSize(), 0.0, 1.0);
            PlayerOverlayScreen.doesNotNeedOxygen = OxygenUtils.entityHasOxygen(player.level(), player) && !(player.isInFluidType());
        }
    }

}
