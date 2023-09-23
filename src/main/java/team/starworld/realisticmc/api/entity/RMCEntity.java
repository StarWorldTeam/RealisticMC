package team.starworld.realisticmc.api.entity;

import earth.terrarium.ad_astra.common.util.ModUtils;
import lombok.Getter;
import net.minecraft.world.entity.Entity;
import team.starworld.realisticmc.util.ArmorUtils;
import team.starworld.realisticmc.util.BlockUtils;
import team.starworld.realisticmc.util.MathUtils;

import java.util.ArrayList;

import static earth.terrarium.ad_astra.common.util.ModUtils.getPlanetGravity;

public class RMCEntity {

    protected @Getter Entity entity;

    public RMCEntity (Entity entity) {
        this.entity = entity;
    }

    public float getBaseGravity () {
        if (entity.isNoGravity()) return 0;
        if (!ModUtils.isSpacelevel(entity.level())) {
            return 1f;
        } else return getPlanetGravity(entity.level());
    }

    public float getBaseWaterPressure () {
        if (!entity.isInFluidType()) return 0;
        var value = BlockUtils.getWaterPressure(entity.blockPosition(), entity.level(), getBaseGravity());
        if (entity.isInLava()) value = value * 3.5f;
        return value;
    }

    public float getWaterPressure () {
        if (!entity.isInFluidType()) return 0;
        float base = getBaseWaterPressure();
        var numbers = new ArrayList <Float> ();
        var length = 0;
        for (var slot : entity.getArmorSlots()) {
            var item = slot.getItem();
            length ++;
            var number = ArmorUtils.getArmorWaterPressureResistant(item);
            if (number.getType() == MathUtils.TypedNumber.Type.PROPORTION) numbers.add(base - Math.abs(base * number.getValue().floatValue()));
            else numbers.add(base - number.getValue().floatValue());
        }
        var value = 0;
        for (var i : numbers) value += i < 0 ? 0 : i;
        value = value / length;
        var result = length >= 4 ? value : base;
        return result < 0 ? 0 : result;
    }

}
