package team.starworld.realisticmc.api.entity;

import lombok.Getter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class RMCLivingEntity extends RMCEntity {

    protected @Getter LivingEntity livingEntity;

    public RMCLivingEntity (Entity entity) {
        super(entity);
        livingEntity = (LivingEntity) entity;
    }

}
