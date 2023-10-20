package team.starworld.realisticmc.util;

import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.ModelSwapper;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import team.starworld.realisticmc.data.resources.ResourceGenerator;

public class ModelUtils {

    public static ModelSwapper MODEL_SWAPPER = new ModelSwapper();

    public static void defaultItemModel (Item item) {
        var key = ForgeRegistries.ITEMS.getKey(item);
        if (key == null) return;
        defaultItemModel(key).accept(item);
    }

    public static NonNullConsumer <Item> defaultItemModel (ResourceLocation texture) {
       return (item) -> {
           var key = ForgeRegistries.ITEMS.getKey(item);
           if (key == null) return;
           ResourceGenerator.INSTANCE.getGenerators().add(
               (generator, manager, pack) -> {
                   var itemModel = new JsonObject();
                   var itemModelTexture = new JsonObject();
                   itemModelTexture.addProperty("layer0", "%s:item/%s".formatted(texture.getNamespace(), texture.getPath()));
                   itemModel.addProperty("parent", "item/generated");
                   itemModel.add("textures", itemModelTexture);
                   pack.addItemModel(key, itemModel);
               }
           );
       };
    }

}
