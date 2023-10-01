package team.starworld.realisticmc.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Config(name = "game-rule")
public class GameRuleConfig implements ConfigData {

    public String[] disableEatUnderTheseArmors = new String[] {
        "create:copper_diving_helmet", "create:netherite_diving_helmet",
        "ad_astra:space_helmet", "ad_astra:netherite_space_helmet", "ad_astra:jet_suit_helmet",
        "realisticmc:diving_mask", "realisticmc:hazmat_respirator"
    };

    public String[] waterPressureResistantArmors = new String[] {
        "create:copper_diving_helmet=17.5%", "create:copper_backtank=17.5%", "create:copper_diving_boots=17.5%",
        "create_sa:copper_leggings=17.5%", "create_sa:copper_boots=17.5%", "create:netherite_diving_helmet=100%",
        "create:netherite_backtank=100%", "minecraft:netherite_leggings=100%", "create:netherite_diving_boots=100%",
        "minecraft:netherite_boots=100%", "ad_astra:space_helmet=80%", "ad_astra:netherite_space_helmet=100%",
        "ad_astra:jet_suit_helmet=100%", "ad_astra:space_suit=80%", "ad_astra:space_pants=80%", "ad_astra:space_boots=80%",
        "ad_astra:netherite_space_suit=100%", "ad_astra:netherite_space_pants=100%", "ad_astra:netherite_space_boots=100%",
        "ad_astra:jet_suit=100%", "ad_astra:jet_suit_pants=100%", "ad_astra:jet_suit_boots=100%",
    };

    public String[] hazmatRemoveEffects = new String[] {
        "slowness", "instant_damage", "nausea", "mining_fatigue", "blindness", "weakness", "poison", "wither",
        "darkness"
    };

    public static Map <Item, String> getWaterPressureResistantArmors () {
        HashMap <Item, String> map = new HashMap <> ();
        var armors = ConfigWrapper.getInstance().gameRule.waterPressureResistantArmors;
        for (var itemName : armors) {
            var value = Objects.requireNonNullElse(Arrays.stream(itemName.split("=")).toList().get(1), "0");
            var item = Arrays.stream(itemName.split("=")).toList().get(0);
            if (item == null) continue;
            var location = ResourceLocation.tryParse(item);
            if (location == null) continue;
            var forgeItem = ForgeRegistries.ITEMS.getValue(location);
            if (forgeItem == null) continue;
            map.put(forgeItem, value);
        }
        return map;
    }

}
