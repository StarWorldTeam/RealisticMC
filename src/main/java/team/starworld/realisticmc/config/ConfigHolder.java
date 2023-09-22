package team.starworld.realisticmc.config;

import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;
import team.starworld.realisticmc.RealisticMinecraft;

@Config(id = RealisticMinecraft.MODID)
public class ConfigHolder {

    public static ConfigHolder INSTANCE;

    @Configurable
    public GameRuleConfigs configGameRule = new GameRuleConfigs();

    public static void init() {
        INSTANCE = Configuration.registerConfig(ConfigHolder.class, ConfigFormats.yaml()).getConfigInstance();
    }

    public static class GameRuleConfigs {

        @Configurable
        public String[] disableEatUnderTheseArmors = new String[] {
            "create:diving_helmet", "create:netherite_diving_helmet",
            "ad_astra:space_helmet", "ad_astra:netherite_space_helmet", "ad_astra:jet_suit_helmet"
        };

    }
}
