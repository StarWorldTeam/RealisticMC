package team.starworld.realisticmc.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.starworld.realisticmc.api.entity.RMCPlayer;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class DebugHud {

    @Inject(method = "getGameInformation", at = @At("RETURN"))
    private void rmc$appendDebugText(CallbackInfoReturnable <List <String>> info) {
        List <String> messages = info.getReturnValue();
        var player = new RMCPlayer(Minecraft.getInstance().player);
        if (player.getEntity().isInFluidType()) messages.add(
            "[RMC] WaterPressure: %s / %s".formatted(
                player.getWaterPressure(), player.getBaseWaterPressure()
            )
        );
    }

}
