package team.starworld.realisticmc.api.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

import static team.starworld.realisticmc.registry.RMCRegistries.rl;


public class RMCDamages {

    public static final ResourceKey <DamageType> WATER_PRESSURE = ResourceKey.create(Registries.DAMAGE_TYPE, rl("water_pressure"));

    public static DamageSource getDamageSource (Level level, ResourceKey <DamageType> type) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type), null, null);
    }

}
