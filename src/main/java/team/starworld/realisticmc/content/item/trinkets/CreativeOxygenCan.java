package team.starworld.realisticmc.content.item.trinkets;

import com.simibubi.create.content.equipment.armor.BacktankItem;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import earth.terrarium.ad_astra.common.item.armor.JetSuit;
import earth.terrarium.ad_astra.common.item.armor.SpaceSuit;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import team.starworld.realisticmc.api.item.SwitchableItem;
import team.starworld.realisticmc.content.item.armor.DivingGear;

public class CreativeOxygenCan extends SwitchableItem {

    public CreativeOxygenCan (Properties properties) {
        super(properties);
    }

    @Override
    public void enabledTick (@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        super.enabledTick(stack, level, entity, slot, selected);
        entity.setAirSupply(entity.getMaxAirSupply());
        ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 255, false, false));
        for (var armor : entity.getArmorSlots()) {
            if (armor.getItem() instanceof BacktankItem backtank) armor.getOrCreateTag().putInt("Air", BacktankUtil.maxAir(armor));
            if (armor.getItem() instanceof DivingGear gear && gear.getType() == ArmorItem.Type.CHESTPLATE) {
                armor.getOrCreateTag().getCompound(
                    "BotariumData"
                ).getList(
                    "StoredFluids", ListTag.TAG_COMPOUND
                ).getCompound(0).putLong(
                    "Amount", gear.getTankSize()
                );
            }
            if (armor.getItem() instanceof SpaceSuit suit) {
                armor.getOrCreateTag().getCompound(
                    "BotariumData"
                ).getList(
                    "StoredFluids", ListTag.TAG_COMPOUND
                ).getCompound(0).putLong(
                    "Amount", suit.getTankSize()
                );
                if (suit instanceof JetSuit jetSuit)
                    armor.getOrCreateTag().getCompound("BotariumData").putLong("Energy", jetSuit.getEnergyStorage(armor).getMaxCapacity());
            }
        }
    }
}