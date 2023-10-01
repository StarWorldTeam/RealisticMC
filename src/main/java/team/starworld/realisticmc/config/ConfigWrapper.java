package team.starworld.realisticmc.config;


import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

import static team.starworld.realisticmc.RealisticMinecraft.MODID;

@Config(name = MODID)
@Config.Gui.Background("cloth-config2:transparent")
public class ConfigWrapper extends PartitioningSerializer.GlobalData {

    public static ConfigWrapper getInstance() {
        return AutoConfig.getConfigHolder(ConfigWrapper.class).getConfig();
    }

    @ConfigEntry.Category("game-rule")
    @ConfigEntry.Gui.TransitiveObject
    public GameRuleConfig gameRule = new GameRuleConfig();

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public ClientConfig client = new ClientConfig();

}