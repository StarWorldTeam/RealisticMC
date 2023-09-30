package team.starworld.realisticmc.util;

import net.minecraft.world.item.ItemStack;

public class ItemStackUtils {

    public static void setUnbreakable (ItemStack stack, boolean unbreakable) {
        stack.getOrCreateTag().putBoolean("Unbreakable", unbreakable);
    }

}
