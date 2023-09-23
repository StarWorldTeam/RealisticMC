package team.starworld.realisticmc.event;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import team.starworld.realisticmc.api.entity.RMCPlayer;
import team.starworld.realisticmc.config.ConfigWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static team.starworld.realisticmc.RealisticMinecraft.MODID;

@Mod.EventBusSubscriber(modid=MODID)
public class PlayerEvent {

    public static class Helper {

        public static Optional <ItemStack> canEat (Player player, ItemStack stack) {
            if (stack.getUseAnimation() == UseAnim.DRINK || stack.getUseAnimation() == UseAnim.EAT) {
                List <String> items = Arrays.stream(ConfigWrapper.getInstance().gameRule.disableEatUnderTheseArmors).toList();
                for (var configItem : items) {
                    for (var armor : player.getArmorSlots()) {
                        try {
                            if (configItem.length() == 0) continue;
                            if (configItem.startsWith("#")) {
                                var tagId = configItem.substring(1);
                                if (tagId.length() == 0) continue;
                                var tags = ForgeRegistries.ITEMS.tags();
                                if (tags == null) continue;
                                var tag = tags.getTag(tags.createTagKey(new ResourceLocation(tagId)));
                                if (tag.stream().anyMatch(i -> i == armor.getItem())) return Optional.of(armor);
                            } else {
                                if (configItem.startsWith("#")) continue;
                                var item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(configItem));
                                if (item == null) continue;
                                if (armor.getItem() == item) return Optional.of(armor);
                            }
                        } catch (Throwable ignored) {}
                    }
                }
                return Optional.empty();
            }
            return Optional.empty();
        }
    }

    @SubscribeEvent
    public static void onRightClickItem (PlayerInteractEvent.RightClickItem event) {
        var player = event.getEntity();
        var item = event.getItemStack();
        var canEat = Helper.canEat(player, item);
        if (canEat.isEmpty() || canEat.get().getItem() == Items.AIR || canEat.get().getCount() < 1) return;
        player.sendSystemMessage(Component.translatable("action.realisticmc.eat_under_armors", canEat.get().getDisplayName(), item.getDisplayName()));
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.FAIL);
    }

    @SubscribeEvent
    public static void onPlayerTick (TickEvent.PlayerTickEvent event) {
        var player = new RMCPlayer(event.player);
        var playerEntity = event.player;
        if (!playerEntity.hasEffect(MobEffects.WATER_BREATHING) && player.getWaterPressure() > 2 && ((playerEntity.level().getGameTime() % 20) == 0) && !playerEntity.isCreative()) {
            playerEntity.setHealth(playerEntity.getHealth() - 1);
        }
    }

}
