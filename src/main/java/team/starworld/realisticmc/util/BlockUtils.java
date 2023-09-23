package team.starworld.realisticmc.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class BlockUtils {

    public static int getWaterDepth (BlockPos pos, Level level) {
        var water = 0;
        while (true) {
            var blockState = level.getBlockState(pos);
            var fluidState = blockState.getFluidState();
            try {
                if (!fluidState.isSource()) break;
            } catch (Throwable ignored) {
                break;
            }
            pos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
            water += 1;
        }
        return water;
    }

    public static float getWaterPressure (BlockPos pos, Level level, float baseGravity) {
        var water = BlockUtils.getWaterDepth(pos, level);
        return (water / 10f) * baseGravity;
    }

}
