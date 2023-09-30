package team.starworld.realisticmc.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import team.starworld.realisticmc.content.item.armor.*;
import team.starworld.realisticmc.content.item.trinkets.*;

import static team.starworld.realisticmc.registry.RMCRegistries.REGISTRATE;
import static team.starworld.realisticmc.registry.RMCRegistries.rl;

public class RMCItems {

    public static final ItemEntry <DivingGear> DIVING_MASK = REGISTRATE.item("diving_mask", (properties) -> new DivingGear(ArmorItem.Type.HELMET, rl("diving_gear"))).register();
    public static final ItemEntry <DivingGear> DIVING_SUIT = REGISTRATE.item("diving_suit", (properties) -> new DivingGear(ArmorItem.Type.CHESTPLATE, rl("diving_gear"))).register();
    public static final ItemEntry <DivingGear> DIVING_LEGGINGS = REGISTRATE.item("diving_leggings", (properties) -> new DivingGear(ArmorItem.Type.LEGGINGS, rl("diving_gear"))).register();
    public static final ItemEntry <DivingGear> DIVING_BOOTS = REGISTRATE.item("diving_boots", (properties) -> new DivingGear(ArmorItem.Type.BOOTS, rl("diving_gear"))).register();
    public static final ItemEntry <CustomSkin> CUSTOM_SKIN_HELMET = REGISTRATE.item("custom_skin_helmet", (properties) -> new CustomSkin(ArmorItem.Type.HELMET, properties)).register();
    public static final ItemEntry <CustomSkin> CUSTOM_SKIN_CHESTPLATE = REGISTRATE.item("custom_skin_chestplate", (properties) -> new CustomSkin(ArmorItem.Type.CHESTPLATE, properties)).register();
    public static final ItemEntry <CustomSkin> CUSTOM_SKIN_LEGGINGS = REGISTRATE.item("custom_skin_leggings", (properties) -> new CustomSkin(ArmorItem.Type.LEGGINGS, properties)).register();
    public static final ItemEntry <CustomSkin> CUSTOM_SKIN_BOOTS = REGISTRATE.item("custom_skin_boots", (properties) -> new CustomSkin(ArmorItem.Type.BOOTS, properties)).register();
    public static final ItemEntry <Item> DIVING_GEAR_FABRIC = REGISTRATE.item("diving_gear_fabric", Item::new).register();
    public static final ItemEntry <CreativeOxygenCan> CREATIVE_OXYGEN_CAN = REGISTRATE.item("creative_oxygen_can", CreativeOxygenCan::new).register();

    public static void init () {}

}
