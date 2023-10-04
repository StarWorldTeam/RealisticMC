package team.starworld.realisticmc.content.item.trinket;

import com.simibubi.create.content.equipment.armor.BacktankItem;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import earth.terrarium.ad_astra.common.item.FluidContainingItem;
import earth.terrarium.ad_astra.common.item.armor.JetSuit;
import earth.terrarium.ad_astra.common.item.armor.SpaceSuit;
import earth.terrarium.ad_astra.common.registry.ModFluids;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
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
import team.starworld.realisticmc.content.item.armor.HazmatGear;

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
            if (
                (armor.getItem() instanceof DivingGear ) || (armor.getItem() instanceof HazmatGear)
            ) {
                ArmorItem item = (ArmorItem) armor.getItem();
                if (item instanceof FluidContainingItem gear) {
                    var holder = new ItemStackHolder(armor);
                    gear.insert(holder, FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), gear.getTankSize() - gear.getFluidAmount(armor), null));
                }
            }
            if (armor.getItem() instanceof SpaceSuit suit) {
                var holder = new ItemStackHolder(armor);
                suit.insert(holder, FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), suit.getTankSize() - suit.getFluidAmount(armor), null));
                if (suit instanceof JetSuit jetSuit) {
                    var storage = EnergyApi.getItemEnergyContainer(new ItemStackHolder(armor));
                    assert storage != null;
                    storage.internalInsert(storage.getMaxCapacity() - storage.getStoredEnergy(), false);
                }
            }
        }
    }

}