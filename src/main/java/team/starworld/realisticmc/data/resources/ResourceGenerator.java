package team.starworld.realisticmc.data.resources;

import lombok.Getter;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynClientResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicTexturePack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static team.starworld.realisticmc.registry.RMCRegistries.rl;

public class ResourceGenerator extends DynClientResourcesGenerator {

    @FunctionalInterface
    public interface Generator {

        void generate (ResourceGenerator generator, ResourceManager manager, DynamicTexturePack pack);

    }

    @Getter
    private final List <Generator> generators = new ArrayList <> ();

    public static final ResourceGenerator INSTANCE = new ResourceGenerator();

    private ResourceGenerator () {
        super(new DynamicTexturePack(rl("generated_pack"), Pack.Position.TOP, false, false));
    }

    @Override
    public Logger getLogger () {
        return LogManager.getLogger(ResourceGenerator.class);
    }

    @Override
    public boolean dependsOnLoadedPacks () {
        return true;
    }

    @Override
    public void regenerateDynamicAssets (ResourceManager resourceManager) {
        generators.forEach(i -> i.generate(this, resourceManager, this.dynamicPack));
        generators.clear();
    }



}
