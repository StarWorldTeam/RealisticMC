package team.starworld.realisticmc.mixin.client;

import earth.terrarium.ad_astra.common.util.OxygenUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.starworld.realisticmc.client.screen.PlayerOverlayScreen;
import team.starworld.realisticmc.content.item.armor.DivingGear;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void rmc$tick (CallbackInfo ci) {
        var player = (LocalPlayer) (Object) this;
        PlayerOverlayScreen.shouldRenderOxygen = DivingGear.hasFullGear(player);
        PlayerOverlayScreen.shouldRenderDivingMaskHud =
            player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof DivingGear gear && gear.getType() == ArmorItem.Type.HELMET;
        if (PlayerOverlayScreen.shouldRenderOxygen) {
            var chest = player.getItemBySlot(EquipmentSlot.CHEST);
            var item = chest.getItem();
            if (item instanceof DivingGear gear)
                PlayerOverlayScreen.oxygenRatio = Mth.clamp(gear.getFluidAmount(chest) / (double) gear.getTankSize(), 0.0, 1.0);
            PlayerOverlayScreen.doesNotNeedOxygen = OxygenUtils.entityHasOxygen(player.level(), player) && player.isInFluidType();
        }
    }

}
