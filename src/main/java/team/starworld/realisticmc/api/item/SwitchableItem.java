package team.starworld.realisticmc.api.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.starworld.realisticmc.registry.RMCRegistries;

public interface SwitchableItem {

    default void enabledTick (ItemStack stack, Level level, Entity entity, @Nullable Integer slot, @Nullable Boolean selected) {}
    default void disabledTick (ItemStack stack, Level level, Entity entity, @Nullable Integer slot, @Nullable Boolean selected) {}

    default boolean isEnabled (ItemStack stack) {
        if (stack.getOrCreateTag().contains(RMCRegistries.rl("enabled").toString())) return stack.getOrCreateTag().getBoolean(RMCRegistries.rl("enabled").toString());
        else return false;
    }

    default void toggle (ItemStack stack) {
        stack.getOrCreateTag().putBoolean(RMCRegistries.rl("enabled").toString(), !this.isEnabled(stack));
    }

    default void tick (ItemStack stack, Level level, Entity entity, @Nullable Integer slot, @Nullable Boolean selected) {
        if (isEnabled(stack)) enabledTick(stack, level, entity, slot, selected);
        else disabledTick(stack, level, entity, slot, selected);
    }

    default @NotNull Component getDefaultName (Component name, @NotNull ItemStack stack) {
        return MutableComponent.create(
            name.getContents()
        ).withStyle(Style.EMPTY.withColor(isEnabled(stack) ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED));
    }

}
