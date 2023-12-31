package team.starworld.realisticmc.registry;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class RMCMachineRecipes {

    public static class GTRecipes {

        public static void initMixerRecipes (Consumer <FinishedRecipe> provider) {
            GTRecipeTypes.MIXER_RECIPES.recipeBuilder(RMCRegistries.rl("bubble_gum"))
                .inputItems(Items.SUGAR, 4)
                .inputFluids(GTMaterials.DyeGreen.getFluid(25))
                .inputFluids(GTMaterials.Rubber.getFluid(500))
                .outputItems(RMCItems.BUBBLE_GUM)
                .EUt(16)
                .duration(120)
                .save(provider);
        }

        public static void initExtruderRecipes (Consumer <FinishedRecipe> provider) {}

        public static void initAssemblerRecipes (Consumer <FinishedRecipe> provider) {
            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(RMCRegistries.rl("electric_loom_hv"))
                .inputItems(GTItems.BATTERY_HV_LITHIUM, 2)
                .inputItems(GTBlocks.MACHINE_CASING_HV.asItem(), 2)
                .inputItems(Items.LOOM, 8)
                .inputItems(TagPrefix.gear, GTMaterials.Titanium, 4)
                .inputItems(TagPrefix.bolt, GTMaterials.Iron, 4)
                .inputItems(TagPrefix.wireFine, GTMaterials.Gold, 4)
                .outputItems(RMCMachines.GTMachines.ELECTRIC_LOOM[GTValues.HV])
                .EUt(128)
                .duration(180)
                .save(provider);
            GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(RMCRegistries.rl("electric_loom_ev"))
                .inputItems(GTItems.BATTERY_EV_VANADIUM, 4)
                .inputItems(GTBlocks.MACHINE_CASING_EV.asItem(), 4)
                .inputItems(Items.LOOM, 12)
                .inputItems(TagPrefix.gear, GTMaterials.Titanium, 8)
                .inputItems(TagPrefix.bolt, GTMaterials.Iron, 16)
                .inputItems(TagPrefix.wireFine, GTMaterials.Gold, 32)
                .outputItems(RMCMachines.GTMachines.ELECTRIC_LOOM[GTValues.EV])
                .EUt(128)
                .duration(180)
                .save(provider);
        }

        public static void initLoomRecipes (Consumer <FinishedRecipe> provider) {
            RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES.recipeBuilder(RMCRegistries.rl("diving_gear_fabric"))
                .inputItems(GTItems.CARBON_MESH, 6)
                .inputItems(TagPrefix.dust, GTMaterials.Iron, 2)
                .inputItems(TagPrefix.dust, GTMaterials.Diamond, 2)
                .inputItems(TagPrefix.dust, GTMaterials.Obsidian, 2)
                .circuitMeta(1)
                .inputFluids(GTMaterials.Rubber.getFluid(1000))
                .inputFluids(GTMaterials.Aluminium.getFluid(1000))
                .EUt(64)
                .duration(60)
                .outputItems(RMCItems.DIVING_GEAR_FABRIC, 2)
                .save(provider);
            RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES.recipeBuilder(RMCRegistries.rl("diving_mask"))
                .inputItems(RMCItems.DIVING_GEAR_FABRIC, 6)
                .inputItems(Tags.Items.GLASS_PANES, 2)
                .inputItems(Tags.Items.GLASS, 1)
                .inputItems(TagPrefix.plate, GTMaterials.Titanium, 8)
                .circuitMeta(2)
                .inputFluids(GTMaterials.Rubber.getFluid(1000))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(1000))
                .EUt(64)
                .duration(60)
                .outputItems(RMCItems.DIVING_MASK, 1)
                .save(provider);
            RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES.recipeBuilder(RMCRegistries.rl("diving_suit"))
                .inputItems(RMCItems.DIVING_GEAR_FABRIC, 8)
                .inputItems(GTItems.FLUID_CELL_LARGE_ALUMINIUM, 2)
                .inputItems(TagPrefix.plate, GTMaterials.Titanium, 4)
                .inputItems(TagPrefix.plate, GTMaterials.Aluminium, 4)
                .circuitMeta(2)
                .inputFluids(GTMaterials.Rubber.getFluid(1000))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(1000))
                .EUt(64)
                .duration(60)
                .outputItems(RMCItems.DIVING_SUIT, 1)
                .save(provider);
            RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES.recipeBuilder(RMCRegistries.rl("diving_leggings"))
                .inputItems(RMCItems.DIVING_GEAR_FABRIC, 8)
                .inputItems(TagPrefix.dust, GTMaterials.Titanium, 8)
                .circuitMeta(2)
                .inputFluids(GTMaterials.Rubber.getFluid(1000))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(1000))
                .EUt(64)
                .duration(60)
                .outputItems(RMCItems.DIVING_LEGGINGS, 1)
                .save(provider);
            RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES.recipeBuilder(RMCRegistries.rl("diving_boots"))
                .inputItems(RMCItems.DIVING_GEAR_FABRIC, 8)
                .inputItems(TagPrefix.dust, GTMaterials.Aluminium, 8)
                .circuitMeta(2)
                .inputFluids(GTMaterials.Rubber.getFluid(1000))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(1000))
                .EUt(64)
                .duration(60)
                .outputItems(RMCItems.DIVING_BOOTS, 1)
                .save(provider);
            RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES.recipeBuilder(RMCRegistries.rl("diving_flipper"))
                .inputItems(RMCItems.DIVING_GEAR_FABRIC, 8)
                .circuitMeta(3)
                .inputFluids(GTMaterials.Rubber.getFluid(1000))
                .EUt(64)
                .duration(60)
                .outputItems(RMCItems.DIVING_FLIPPER, 1)
                .save(provider);
            RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES.recipeBuilder(RMCRegistries.rl("hazmat_gear_fabric"))
                .inputItems(GTItems.CARBON_MESH, 6)
                .inputItems(TagPrefix.dust, GTMaterials.Lead, 4)
                .inputItems(TagPrefix.plate, GTMaterials.Rubber, 2)
                .circuitMeta(1)
                .inputFluids(GTMaterials.Rubber.getFluid(1000))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(1000))
                .EUt(64)
                .duration(60)
                .outputItems(RMCItems.HAZMAT_GEAR_FABRIC, 2)
                .save(provider);
            RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES.recipeBuilder(RMCRegistries.rl("hazmat_respirator"))
                .inputItems(RMCItems.HAZMAT_GEAR_FABRIC, 6)
                .inputItems(Tags.Items.GLASS_PANES, 2)
                .inputItems(TagPrefix.pipeNormalFluid, GTMaterials.Aluminium, 2)
                .inputItems(TagPrefix.plate, GTMaterials.Lead, 8)
                .circuitMeta(2)
                .inputFluids(GTMaterials.Rubber.getFluid(1000))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(1000))
                .EUt(64)
                .duration(60)
                .outputItems(RMCItems.HAZMAT_RESPIRATOR, 1)
                .save(provider);
            RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES.recipeBuilder(RMCRegistries.rl("hazmat_oxygen_supplier"))
                .inputItems(RMCItems.HAZMAT_GEAR_FABRIC, 8)
                .inputItems(GTItems.FLUID_CELL_LARGE_TUNGSTEN_STEEL, 2)
                .inputItems(TagPrefix.dust, GTMaterials.Lead, 4)
                .inputItems(TagPrefix.pipeNormalFluid, GTMaterials.Aluminium, 2)
                .circuitMeta(2)
                .inputFluids(GTMaterials.Rubber.getFluid(1000))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(1000))
                .EUt(64)
                .duration(60)
                .outputItems(RMCItems.HAZMAT_OXYGEN_SUPPLIER, 1)
                .save(provider);
            RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES.recipeBuilder(RMCRegistries.rl("hazmat_leggings"))
                .inputItems(RMCItems.HAZMAT_GEAR_FABRIC, 8)
                .inputItems(TagPrefix.plate, GTMaterials.Rubber)
                .inputItems(TagPrefix.dust, GTMaterials.Lead, 8)
                .circuitMeta(2)
                .inputFluids(GTMaterials.Rubber.getFluid(1000))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(1000))
                .EUt(64)
                .duration(60)
                .outputItems(RMCItems.HAZMAT_LEGGINGS, 1)
                .save(provider);
            RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES.recipeBuilder(RMCRegistries.rl("hazmat_boots"))
                .inputItems(RMCItems.HAZMAT_GEAR_FABRIC, 8)
                .inputItems(TagPrefix.plate, GTMaterials.Rubber)
                .inputItems(TagPrefix.plate, GTMaterials.Lead, 8)
                .circuitMeta(2)
                .inputFluids(GTMaterials.Rubber.getFluid(1000))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(1000))
                .EUt(64)
                .duration(60)
                .outputItems(RMCItems.HAZMAT_BOOTS, 1)
                .save(provider);
        }

    }

    public static void initGTRecipes (Consumer <FinishedRecipe> provider) {
        GTRecipes.initAssemblerRecipes(provider);
        GTRecipes.initLoomRecipes(provider);
        GTRecipes.initMixerRecipes(provider);
        GTRecipes.initExtruderRecipes(provider);
    }

}
