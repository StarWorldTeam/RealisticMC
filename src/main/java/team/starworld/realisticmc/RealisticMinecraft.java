package team.starworld.realisticmc;

import com.mojang.logging.LogUtils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
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
import team.starworld.realisticmc.api.item.ComponentItem;
import team.starworld.realisticmc.api.item.armor.model.ArmorFullModel;
import team.starworld.realisticmc.client.screen.PlayerOverlayScreen;
import team.starworld.realisticmc.config.ConfigWrapper;
import team.starworld.realisticmc.registry.RMCRegistrate;
import team.starworld.realisticmc.registry.RMCRegistries;

import java.util.ArrayList;

@Mod(RealisticMinecraft.MODID)
public class RealisticMinecraft {

    public static final String MODID = "realisticmc";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RealisticMinecraft () {
        AutoConfig.register(ConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        RMCRegistries.REGISTRATE.registerEventListeners(modEventBus);
        RMCRegistries.init();

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::entityLayerSetup);

        try {
            Class <?>  minecraft = net.minecraft.client.Minecraft.class;
            MinecraftForge.EVENT_BUS.addListener(this::onRegisterClientHud);
        } catch (Throwable ignored) {}
    }

    private void entityLayerSetup (final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ArmorFullModel.FULL_ARMOR_LAYER, ArmorFullModel::createBodyLayer);
    }

    private void commonSetup (final FMLCommonSetupEvent event) {}
    private void addCreative (BuildCreativeModeTabContentsEvent event) {
        var tab = event.getTab();
        var items = new ArrayList <ItemStack> ();
        for (var registrate : RMCRegistrate.INSTANCES) {
            for (var itemEntry : registrate.getAll(Registries.ITEM)) {
                var item = itemEntry.get();
                if (item instanceof BlockItem) continue;
                if (item instanceof ComponentItem component) component.fillItemCategory(tab, items);
            }
            for (var blockEntry : registrate.getAll(Registries.BLOCK)) {
                var block = blockEntry.get();
                if (block instanceof ComponentItem component) component.fillItemCategory(tab, items);
            }
        }
        items.forEach(event::accept);
    }

    private void onRegisterClientHud (RenderGuiEvent.Post event) {
        PlayerOverlayScreen.render(event.getGuiGraphics(), event.getPartialTick());
    }

    @SubscribeEvent
    public void onServerStarting (ServerStartingEvent event) {}

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup (FMLClientSetupEvent event) {}

    }

}
