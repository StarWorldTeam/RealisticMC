package team.starworld.realisticmc.api.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import team.starworld.realisticmc.registry.RMCRegistries;

public class SwitchableItem extends Item {

    public SwitchableItem (Properties properties) {
        super(properties.stacksTo(1));
    }

    public void enabledTick (ItemStack stack, Level level, Entity entity, int slotId, boolean selected) {}
    public void disabledTick (ItemStack stack, Level level, Entity entity, int slot, boolean selected) {}

    @Override
    public void inventoryTick (@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean selected) {
        if (this.isEnabled(stack)) this.enabledTick(stack, level, entity, slotId, selected);
        else this.disabledTick(stack, level, entity, slotId, selected);
    }

    public boolean isEnabled (ItemStack stack) {
        if (stack.getOrCreateTag().contains(RMCRegistries.rl("enabled").toString())) return stack.getOrCreateTag().getBoolean(RMCRegistries.rl("enabled").toString());
        else return false;

    }

    public void toggle (ItemStack stack) {
        stack.getOrCreateTag().putBoolean(RMCRegistries.rl("enabled").toString(), !this.isEnabled(stack));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use (@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var tag = stack.getOrCreateTag();
        this.toggle(stack);
        return super.use(level, player, hand);
    }

    @Override
    public @NotNull Component getName (@NotNull ItemStack stack) {
        return MutableComponent.create(
            super.getName(stack).getContents()
        ).withStyle(Style.EMPTY.withColor(isEnabled(stack) ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED));
    }

}
