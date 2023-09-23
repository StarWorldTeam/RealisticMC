package team.starworld.realisticmc.util;

import net.minecraft.world.item.Item;
import team.starworld.realisticmc.config.GameRuleConfig;

public class ArmorUtils {

    public static MathUtils.TypedNumber getArmorWaterPressureResistant (Item item) {
        try {
            var value = GameRuleConfig.getWaterPressureResistantArmors().get(item);
            if (value == null) throw new Exception();
            if (value.endsWith("%")) return new MathUtils.TypedNumber(MathUtils.TypedNumber.Type.PROPORTION, Float.parseFloat(value.replaceAll("%", "")) / 100f);
            else return new MathUtils.TypedNumber(MathUtils.TypedNumber.Type.NUMBER, Float.parseFloat(value));
        } catch (Throwable e) {
            return new MathUtils.TypedNumber(MathUtils.TypedNumber.Type.NUMBER, 0);
        }
    }

}
