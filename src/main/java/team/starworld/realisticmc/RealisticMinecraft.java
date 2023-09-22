package team.starworld.realisticmc;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import team.starworld.realisticmc.config.ConfigHolder;
import team.starworld.realisticmc.registry.RMCRegistries;

@Mod(RealisticMinecraft.MODID)
public class RealisticMinecraft {

    public static final String MODID = "realisticmc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RealisticMinecraft () {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ConfigHolder.init();
        RMCRegistries.init();

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup (final FMLCommonSetupEvent event) {}

    private void addCreative (BuildCreativeModeTabContentsEvent event) {}

    @SubscribeEvent
    public void onServerStarting (ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup (FMLClientSetupEvent event) {}

    }

}
