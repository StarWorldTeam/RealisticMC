package team.starworld.realisticmc.api.entity;

import lombok.Getter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class RMCPlayer extends RMCLivingEntity {

    protected @Getter Player player;

    public RMCPlayer (Entity entity) {
        super(entity);
        player = (Player) entity;
    }

}
