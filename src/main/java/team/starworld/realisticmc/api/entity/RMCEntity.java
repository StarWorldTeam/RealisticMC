package team.starworld.realisticmc.api.entity;

import earth.terrarium.ad_astra.common.util.ModUtils;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

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

    public float getWaterPressure () {
        if (!entity.isInFluidType()) return 0;
        var water = 0;
        var pos = entity.blockPosition();
        var level = entity.level();
        while (true) {
            var block = level.getBlockEntity(pos);
            if (block == null) break;
            var blockState = block.getBlockState();
            var fluidState = blockState.getFluidState();
            try {
                if (fluidState.isSource()) break;
            } catch (Throwable ignored) {
                return water;
            }
            pos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
            water += 1;
        }
        return (water / 10f) * getBaseGravity();
    }

}
