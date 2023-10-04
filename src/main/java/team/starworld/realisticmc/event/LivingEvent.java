package team.starworld.realisticmc.event;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.starworld.realisticmc.api.item.armor.RMCArmor;

import static team.starworld.realisticmc.RealisticMinecraft.MODID;

@Mod.EventBusSubscriber(modid=MODID)
public class LivingEvent {

    @SubscribeEvent
    public static void onPlayerHurt (LivingHurtEvent event) {
        var entity = event.getEntity();
        for (var item : entity.getArmorSlots()) {
            if (item.getItem() instanceof RMCArmor armor) {
                if (armor.isDamageImmune(event.getSource(), entity)) {
                    event.setAmount(0);
                    event.setCanceled(true);
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

}
