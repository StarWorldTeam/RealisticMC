package team.starworld.realisticmc.registry;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;

public class RMCRecipeTypes {

    public static class GTRecipeTypes {

        public static GTRecipeType ELECTRIC_LOOM_RECIPES;

        @SuppressWarnings("deprecation")
        public static GTRecipeType register (String name, String group, RecipeType <?>... proxyRecipes) {
            GTRecipeType recipeType = new GTRecipeType(RMCRegistries.rl(name), group, proxyRecipes);
            GTRegistries.register(BuiltInRegistries.RECIPE_TYPE, recipeType.registryName, recipeType);
            GTRegistries.register(BuiltInRegistries.RECIPE_SERIALIZER, recipeType.registryName, new GTRecipeSerializer());
            GTRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);
            return recipeType;
        }

        public static void init () {
            ELECTRIC_LOOM_RECIPES = register("electric_loom", com.gregtechceu.gtceu.common.data.GTRecipeTypes.ELECTRIC)
                .setEUIO(IO.IN)
                .setMaxIOSize(8, 2, 2, 0)
                .setSlotOverlay(false, false, GuiTextures.SLOT)
                .setSlotOverlay(false, true, GuiTextures.SLOT_DARKENED)
                .setSlotOverlay(true, false, GuiTextures.SLOT)
                .setMaxTooltips(4)
                .setSound(GTSoundEntries.WIRECUTTER_TOOL)
                .setProgressBar(GuiTextures.PROGRESS_BAR_CANNER, ProgressTexture.FillDirection.LEFT_TO_RIGHT);
        }
    }

}
