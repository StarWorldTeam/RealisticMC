package team.starworld.realisticmc.registry;

import com.tterrag.registrate.AbstractRegistrate;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class RMCRegistrate extends AbstractRegistrate <RMCRegistrate> {

    public static final List <RMCRegistrate> INSTANCES = new ArrayList <> ();

    public RMCRegistrate (String modid) {
        super(modid);
        INSTANCES.add(this);
    }

    @Override
    public @NotNull RMCRegistrate registerEventListeners (@NotNull IEventBus bus) {
        return super.registerEventListeners(bus);
    }

}
