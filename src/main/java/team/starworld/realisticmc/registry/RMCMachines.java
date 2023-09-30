package team.starworld.realisticmc.registry;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import it.unimi.dsi.fastutil.ints.Int2LongFunction;

public class RMCMachines {

    public static class GTMachines {

        public static MachineDefinition[] ELECTRIC_LOOM;

        public static MachineDefinition[] registerSimpleMachines (String name, GTRecipeType type, Int2LongFunction tankScalingFunction, int... tier) {
            return com.gregtechceu.gtceu.common.data.GTMachines.registerSimpleMachines("rmc_" + name, type, tankScalingFunction, tier);
        }

        public static void init () {
            ELECTRIC_LOOM = registerSimpleMachines(
                "electric_loom", RMCRecipeTypes.GTRecipeTypes.ELECTRIC_LOOM_RECIPES, (tier) -> tier * 64000L,
                GTValues.HV, GTValues.EV
            );
        }

    }

}
