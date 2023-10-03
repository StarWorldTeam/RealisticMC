package team.starworld.realisticmc.content.item.armor;

import earth.terrarium.ad_astra.common.item.FluidContainingItem;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.starworld.realisticmc.api.item.armor.RMCArmor;
import team.starworld.realisticmc.config.ConfigWrapper;
import team.starworld.realisticmc.util.ArmorUtils;
import team.starworld.realisticmc.util.ItemStackUtils;
import team.starworld.realisticmc.util.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class HazmatGear extends ArmorItem implements RMCArmor, FluidContainingItem {

    protected ResourceLocation name;


    public static List <MobEffect> getEffects () {
        ArrayList <MobEffect> list = new ArrayList <> ();
        for (var id : ConfigWrapper.getInstance().gameRule.hazmatRemoveEffects) {
            try {
                var location = new ResourceLocation(id.contains(":") ? id : "minecraft:" + id);
                list.add(ForgeRegistries.MOB_EFFECTS.getValue(location));
            } catch (Throwable ignored) {}
        }
        return list;
    }

    public HazmatGear (Type type, ResourceLocation name) {
        super(ArmorMaterials.DIAMOND, type, new Item.Properties().durability(Integer.MAX_VALUE));
        this.name = name;
    }

    public static boolean hasFullGear (LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof HazmatGear &&
            entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof HazmatGear &&
            entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof HazmatGear &&
            entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof HazmatGear;
    }

    @Override
    public int getMaxDamage (ItemStack stack) { return Integer.MAX_VALUE; }

    @Override
    public int getDamage (ItemStack stack) { return 0; }

    @Override
    public void setDamage (ItemStack stack, int damage) { super.setDamage(stack, 0); }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient (Consumer <IClientItemExtensions> consumer) {
        consumer.accept(DivingGear.Rendering.INSTANCE);
    }

    @Override
    public void inventoryTick (@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        ItemStackUtils.setUnbreakable(stack, true);
    }

    @Override
    public int getBarWidth (@NotNull ItemStack stack) { return Math.round(((float) getFluidAmount(stack) * 13f / (float) getTankSize())); }

    @Override
    public int getBarColor (@NotNull ItemStack stack) { return 0xA8D8F9; }

    @Override
    public boolean isBarVisible (@NotNull ItemStack stack) {
        return getFluidAmount(stack) != getTankSize();
    }

    @Override
    public void onArmorTick (ItemStack stack, Level level, Player player) {
        setDamage(stack, 0);
        if (!hasFullGear(player)) return;
        if (stack.getItem() instanceof HazmatGear gear && gear.getType() == Type.CHESTPLATE) {
            if (player.level().getGameTime() % 40 == 0 && gear.getFluidAmount(stack) <= 0) {
                player.hurt(player.damageSources().drown(), 2);
            }
            if (gear.getFluidAmount(stack) > 0) {
                getEffects().forEach(player::removeEffect);
                player.clearFire();
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40, 255, false, false));
                if (player.level().getGameTime() % 40 == 0) {
                    var holder = new ItemStackHolder(stack);
                    extract(holder, FluidHooks.newFluidHolder(getFluid(holder.getStack()), 5, null));
                }
            }
        }
    }

    @Override
    public MathUtils.TypedFloatNumber getArmorWaterPressureResistant () {
        return new MathUtils.TypedFloatNumber(1, 1);
    }

    @Override
    public long getTankSize () {
        return this.getType() == Type.CHESTPLATE ? 640000 : 0;
    }

    @Override
    public BiPredicate <Integer, FluidHolder> getFilter () {
        return (ignored, holder) -> holder.is(ModTags.OXYGEN);
    }

    @Override
    public void appendHoverText (@NotNull ItemStack stack, @Nullable Level level, @NotNull List <Component> list, @NotNull TooltipFlag flag) {
        if (this.getType() == Type.CHESTPLATE)
            list.add(Component.translatable("tooltip.realisticmc.oxygen_stored", getFluidAmount(stack), getTankSize()));
    }

    @Override
    public @Nullable String getArmorTexture (ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return ArmorUtils.getArmorTexture(name);
    }

}
