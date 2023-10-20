package team.starworld.realisticmc.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import team.starworld.realisticmc.content.item.armor.CustomSkin;
import team.starworld.realisticmc.content.item.armor.DivingGear;
import team.starworld.realisticmc.content.item.armor.HazmatGear;
import team.starworld.realisticmc.content.item.trinket.CreativeOxygenCan;
import team.starworld.realisticmc.util.ModelUtils;

import static team.starworld.realisticmc.registry.RMCRegistries.REGISTRATE;
import static team.starworld.realisticmc.registry.RMCRegistries.rl;

public class RMCItems {

    public static final ItemEntry <DivingGear> DIVING_MASK = REGISTRATE.item("diving_mask", (properties) -> new DivingGear(ArmorItem.Type.HELMET, rl("diving_gear"))).onRegister(ModelUtils::defaultItemModel).register();
    public static final ItemEntry <DivingGear> DIVING_SUIT = REGISTRATE.item("diving_suit", (properties) -> new DivingGear(ArmorItem.Type.CHESTPLATE, rl("diving_gear"))).onRegister(ModelUtils::defaultItemModel).register();
    public static final ItemEntry <DivingGear> DIVING_LEGGINGS = REGISTRATE.item("diving_leggings", (properties) -> new DivingGear(ArmorItem.Type.LEGGINGS, rl("diving_gear"))).onRegister(ModelUtils::defaultItemModel).register();
    public static final ItemEntry <DivingGear> DIVING_BOOTS = REGISTRATE.item("diving_boots", (properties) -> new DivingGear(ArmorItem.Type.BOOTS, rl("diving_gear"))).onRegister(ModelUtils::defaultItemModel).register();
    public static final ItemEntry <HazmatGear> HAZMAT_RESPIRATOR = REGISTRATE.item("hazmat_respirator", (properties) -> new HazmatGear(ArmorItem.Type.HELMET, rl("hazmat_gear"))).onRegister(ModelUtils::defaultItemModel).register();
    public static final ItemEntry <HazmatGear> HAZMAT_OXYGEN_SUPPLIER = REGISTRATE.item("hazmat_oxygen_supplier", (properties) -> new HazmatGear(ArmorItem.Type.CHESTPLATE, rl("hazmat_gear"))).onRegister(ModelUtils::defaultItemModel).register();
    public static final ItemEntry <HazmatGear> HAZMAT_LEGGINGS = REGISTRATE.item("hazmat_leggings", (properties) -> new HazmatGear(ArmorItem.Type.LEGGINGS, rl("hazmat_gear"))).onRegister(ModelUtils::defaultItemModel).register();
    public static final ItemEntry <HazmatGear> HAZMAT_BOOTS = REGISTRATE.item("hazmat_boots", (properties) -> new HazmatGear(ArmorItem.Type.BOOTS, rl("hazmat_gear"))).onRegister(ModelUtils::defaultItemModel).register();
    public static final ItemEntry <CustomSkin> CUSTOM_SKIN_HELMET = REGISTRATE.item("custom_skin_helmet", (properties) -> new CustomSkin(ArmorItem.Type.HELMET, properties)).onRegister(ModelUtils.defaultItemModel(RMCRegistries.rlMinecraft("empty_armor_slot_helmet"))).register();
    public static final ItemEntry <CustomSkin> CUSTOM_SKIN_CHESTPLATE = REGISTRATE.item("custom_skin_chestplate", (properties) -> new CustomSkin(ArmorItem.Type.CHESTPLATE, properties)).onRegister(ModelUtils.defaultItemModel(RMCRegistries.rlMinecraft("empty_armor_slot_chestplate"))).register();
    public static final ItemEntry <CustomSkin> CUSTOM_SKIN_LEGGINGS = REGISTRATE.item("custom_skin_leggings", (properties) -> new CustomSkin(ArmorItem.Type.LEGGINGS, properties)).onRegister(ModelUtils.defaultItemModel(RMCRegistries.rlMinecraft("empty_armor_slot_leggings"))).register();
    public static final ItemEntry <CustomSkin> CUSTOM_SKIN_BOOTS = REGISTRATE.item("custom_skin_boots", (properties) -> new CustomSkin(ArmorItem.Type.BOOTS, properties)).onRegister(ModelUtils.defaultItemModel(RMCRegistries.rlMinecraft("empty_armor_slot_boots"))).register();
    public static final ItemEntry <Item> DIVING_GEAR_FABRIC = REGISTRATE.item("diving_gear_fabric", Item::new).onRegister(ModelUtils::defaultItemModel).register();
    public static final ItemEntry <Item> HAZMAT_GEAR_FABRIC = REGISTRATE.item("hazmat_gear_fabric", Item::new).onRegister(ModelUtils::defaultItemModel).register();
    public static final ItemEntry <CreativeOxygenCan> CREATIVE_OXYGEN_CAN = REGISTRATE.item("creative_oxygen_can", CreativeOxygenCan::new).onRegister(ModelUtils::defaultItemModel).register();

    public static void init () {}

}
