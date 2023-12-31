package team.starworld.realisticmc.registry;

import com.gregtechceu.gtceu.api.addon.IGTAddon;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import team.starworld.realisticmc.data.resources.ResourceGenerator;

import java.util.function.Consumer;

import static team.starworld.realisticmc.RealisticMinecraft.MODID;

public class RMCRegistries {

    public static final RMCRegistrate REGISTRATE = new RMCRegistrate(MODID);

    public static ResourceLocation rl (String path) {
        return new ResourceLocation(MODID, path);
    }

    public static ResourceLocation rlMinecraft (String path) {
        return new ResourceLocation("minecraft", path);
    }

    public static ResourceLocation rlPlatform (String path) {
        return new ResourceLocation("forge", path);
    }

    public static void init () {
        RMCItems.init();
        RMCBlocks.init();
        RMCBlockEntities.init();
    }

    @com.gregtechceu.gtceu.api.addon.GTAddon
    public static class GTAddon implements IGTAddon {

        @Override
        public void initializeAddon () {
            ResourceGenerator.INSTANCE.register();
        }

        @Override
        public String addonModId () {
            return MODID;
        }

        @Override
        public void registerMaterials () {
            RMCMaterials.GTMaterials.init();
        }

        @Override
        public void registerTagPrefixes () {}

        @Override
        public void registerRecipeTypes () {
            RMCRecipeTypes.GTRecipeTypes.init();
        }

        @Override
        public void registerMachines () {
            RMCMachines.GTMachines.init();
        }

        @Override
        public void initializeRecipes (Consumer <FinishedRecipe> provider) {
            RMCMachineRecipes.initGTRecipes(provider);
        }

    }

}
