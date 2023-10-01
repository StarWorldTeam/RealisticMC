package team.starworld.realisticmc.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.ad_astra.client.screen.GuiUtil;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import team.starworld.realisticmc.config.ConfigWrapper;

import static team.starworld.realisticmc.registry.RMCRegistries.rl;

public class PlayerOverlayScreen {

    public static boolean shouldRenderOxygen = false;

    public static boolean doesNotNeedOxygen = true;

    public static boolean shouldRenderDivingMaskHud = false;

    public static final ResourceLocation OXYGEN_TANK_EMPTY_TEXTURE = rl("textures/gui/overlay/diving_gear_oxygen_empty.png");
    public static final ResourceLocation OXYGEN_TANK_FULL_TEXTURE = rl("textures/gui/overlay/diving_gear_oxygen_full.png");
    public static final ResourceLocation DIVING_MASK_HUD = rl("textures/misc/diving_mask_hud.png");

    public static double oxygenRatio = 0;

    public static void render (GuiGraphics graphics, float delta) {
        PoseStack poseStack = graphics.pose();
        Minecraft minecraft = Minecraft.getInstance();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        if (player.isSpectator()) {
            return;
        }
        if (ConfigWrapper.getInstance().client.showScreenMaskInDivingGear && shouldRenderDivingMaskHud && Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            GuiUtil.drawVertical(graphics, 0, 0, screenWidth, screenHeight, DIVING_MASK_HUD, 1);
        }
        if (shouldRenderOxygen && !minecraft.options.renderDebug) {
            poseStack.pushPose();
            poseStack.scale(AdAstraConfig.oxygenBarScale, AdAstraConfig.oxygenBarScale, AdAstraConfig.oxygenBarScale);
            int x = 5 + AdAstraConfig.oxygenBarXOffset;
            int y = 25 + AdAstraConfig.oxygenBarYOffset;

            int textureWidth = 62;
            int textureHeight = 52;

            GuiUtil.drawVerticalReverse(graphics, x, y, textureWidth, textureHeight, OXYGEN_TANK_EMPTY_TEXTURE, oxygenRatio);
            GuiUtil.drawVertical(graphics, x, y, textureWidth, textureHeight, OXYGEN_TANK_FULL_TEXTURE, oxygenRatio);

            double oxygen = Math.round(oxygenRatio * 1000) / 10.0;
            Component text = Component.nullToEmpty((oxygen) + "%");
            int textWidth = minecraft.font.width(text);
            if (doesNotNeedOxygen) {
                graphics.drawString(minecraft.font, text, (int) (x + (textureWidth - textWidth) / 2.0f), y + textureHeight + 3, 0x7FFF00);
            } else {
                graphics.drawString(minecraft.font, text, (int) (x + (textureWidth - textWidth) / 2.0f), y + textureHeight + 3, oxygen <= 0.0f ? 0xDC143C : 0xFFFFFF);
            }
            poseStack.popPose();
        }
    }

}
