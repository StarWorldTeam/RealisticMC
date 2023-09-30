package team.starworld.realisticmc.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import team.starworld.realisticmc.api.item.armor.RMCArmor;
import team.starworld.realisticmc.config.GameRuleConfig;

import java.util.Locale;

public class ArmorUtils {

    public static MathUtils.TypedFloatNumber getArmorWaterPressureResistant (Item item) {
        try {
            if (item instanceof RMCArmor armor) return armor.getArmorWaterPressureResistant(item);
            var value = GameRuleConfig.getWaterPressureResistantArmors().get(item);
            if (value == null) throw new Exception();
            if (value.endsWith("%")) return new MathUtils.TypedFloatNumber(MathUtils.TypedFloatNumber.Type.PROPORTION, Float.parseFloat(value.replaceAll("%", "")) / 100f);
            else return new MathUtils.TypedFloatNumber(MathUtils.TypedFloatNumber.Type.NUMBER, Float.parseFloat(value));
        } catch (Throwable e) {
            return new MathUtils.TypedFloatNumber(MathUtils.TypedFloatNumber.Type.NUMBER, 0);
        }
    }

    public static MathUtils.TypedFloatNumber getArmorWaterPressureResistant (ItemStack stack) {
        var item = stack.getItem();
        try {
            if (item instanceof RMCArmor armor) return armor.getArmorWaterPressureResistant(stack);
            return getArmorWaterPressureResistant(item);
        } catch (Throwable e) {
            return new MathUtils.TypedFloatNumber(MathUtils.TypedFloatNumber.Type.NUMBER, 0);
        }
    }

    public static String getArmorTexture (ItemStack stack, EquipmentSlot slot, String type) {
        var key = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (key == null) return null;
        return getArmorTexture(key, slot, type);
    }

    public static String getArmorTexture (ResourceLocation key, EquipmentSlot slot, String type) {
        return String.format(
            Locale.ROOT,
            "%s:textures/models/armor/%s_layer_%d%s.png",
            key.getNamespace(), key.getPath(),
            slot == EquipmentSlot.LEGS ? 2 : 1,
            type == null ? "" : String.format(Locale.ROOT, "_%s", type)
        );
    }

    public static String getArmorTexture (ResourceLocation key, EquipmentSlot slot) {
        return String.format(
            Locale.ROOT,
            "%s:textures/models/armor/%s_layer_%d.png",
            key.getNamespace(), key.getPath(),
            slot == EquipmentSlot.LEGS ? 2 : 1
        );
    }

    public static String getArmorTexture (String namespace, String path) {
        return String.format(
            Locale.ROOT,
            "%s:%s",
            namespace, path
        );
    }


    public static String getArmorTexture (ResourceLocation key) {
        return String.format(
            Locale.ROOT,
            "%s:textures/models/armor/%s.png",
            key.getNamespace(), key.getPath()
        );
    }

}
