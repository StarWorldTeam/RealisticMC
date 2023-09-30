package team.starworld.realisticmc.api.item.armor;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import team.starworld.realisticmc.util.MathUtils;

public interface RMCArmor {

    default MathUtils.TypedFloatNumber getArmorWaterPressureResistant () { return MathUtils.TypedFloatNumber.zero(); }
    default MathUtils.TypedFloatNumber getArmorWaterPressureResistant (ItemStack stack) { return this.getArmorWaterPressureResistant(stack.getItem()); }
    default MathUtils.TypedFloatNumber getArmorWaterPressureResistant (Item item) { return this.getArmorWaterPressureResistant(); }

}
