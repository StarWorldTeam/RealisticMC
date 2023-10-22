package team.starworld.realisticmc.util;

import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.ModelSwapper;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import team.starworld.realisticmc.api.item.armor.model.ArmorFullModel;
import team.starworld.realisticmc.data.resources.ResourceGenerator;

public class ModelUtils {

    public static class FullArmorRendering implements IClientItemExtensions {

        public static final FullArmorRendering INSTANCE = new FullArmorRendering();

        private FullArmorRendering () {}


        @Override
        public @NotNull HumanoidModel <?> getHumanoidArmorModel (LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel <?> original) {
            return equipmentSlot == EquipmentSlot.LEGS || equipmentSlot == EquipmentSlot.FEET ? original : ArmorFullModel.INSTANCE.get();
        }

    }

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
