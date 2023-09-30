package team.starworld.realisticmc.api.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ComponentItem {

    default void fillItemCategory (@NotNull CreativeModeTab tab, List <ItemStack> items) {}

}
