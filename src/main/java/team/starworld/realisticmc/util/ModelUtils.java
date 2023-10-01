package team.starworld.realisticmc.util;

import com.simibubi.create.foundation.utility.ModelSwapper;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import net.minecraft.world.item.Item;

public class ModelUtils {

    public static ModelSwapper MODEL_SWAPPER = new ModelSwapper();

    public static <T extends Item> void defaultModel (DataGenContext <Item, T> ctx, RegistrateItemModelProvider prov) {
        prov.generated(ctx::getEntry);
    }

}
