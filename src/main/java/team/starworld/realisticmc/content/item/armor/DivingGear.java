package team.starworld.realisticmc.content.item.armor;

import com.gregtechceu.gtceu.common.data.GTMaterials;
import earth.terrarium.ad_astra.common.item.FluidContainingItem;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyItem;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedItemEnergyContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.starworld.realisticmc.api.item.armor.RMCArmor;
import team.starworld.realisticmc.util.ArmorUtils;
import team.starworld.realisticmc.util.ItemStackUtils;
import team.starworld.realisticmc.util.MathUtils;
import team.starworld.realisticmc.util.ModelUtils;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;


public class DivingGear extends ArmorItem implements RMCArmor, FluidContainingItem, BotariumEnergyItem <WrappedItemEnergyContainer> {

    protected final ResourceLocation name;

    public DivingGear (Type type, ResourceLocation name) {
        super(ArmorMaterials.DIAMOND, type, new Item.Properties().durability(Integer.MAX_VALUE));
        this.name = name;
    }

    public static void bootsTick (LivingEntity entity) {
        if (entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof DivingGear gear && gear.getType() == Type.BOOTS) {
            entity.setSwimming(false);
            Vec3 motion = entity.getDeltaMovement();
            entity.setOnGround(entity.onGround() || entity.verticalCollision);
            var isJumping = entity.jumping;
            if (isJumping && entity.onGround()) {
                motion = motion.add(0.0, 0.5, 0.0);
                entity.setOnGround(false);
            } else {
                motion = motion.add(0.0, -0.05000000074505806, 0.0);
            }
            float multiplier = 1.3F;
            if (motion.multiply(1.0, 0.0, 1.0).length() < 0.14499999582767487 && (entity.zza > 0.0F || entity.xxa != 0.0F) && !entity.isShiftKeyDown()) {
                motion = motion.multiply(multiplier, 1.0, multiplier);
            }
            entity.setDeltaMovement(motion);
        }
    }

    public static boolean hasFullGear (LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof DivingGear &&
            entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DivingGear;
    }

    public static boolean hasLeggings (LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof DivingGear;
    }

    public static boolean hasBoots (LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof DivingGear;
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
        consumer.accept(ModelUtils.FullArmorRendering.INSTANCE);
    }

    @Override
    public WrappedItemEnergyContainer getEnergyStorage (ItemStack stack) {
        return new WrappedItemEnergyContainer(
            stack,
            new SimpleEnergyContainer(640000) {
                public long maxInsert() {
                return 640000L;
            }
                public long maxExtract() {
                    return 640000L;
            }
            }
        );
    }


    @Override
    public void inventoryTick (@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        ItemStackUtils.setUnbreakable(stack, true);
    }

    @Override
    public int getBarWidth (@NotNull ItemStack stack) {
        if (getType() == Type.CHESTPLATE) return Math.round(((float) getFluidAmount(stack) * 13f / (float) getTankSize()));
        if (getType() == Type.HELMET) return Math.round(((float) getEnergyStorage(stack).getStoredEnergy() * 13f / (float) getEnergyStorage(stack).getMaxCapacity()));
        return 0;
    }

    @Override
    public int getBarColor (@NotNull ItemStack stack) {
        return 0xA8D8F9;
    }

    @Override
    public boolean isBarVisible (@NotNull ItemStack stack) {
        if (getType() == Type.CHESTPLATE) return getFluidAmount(stack) < getTankSize();
        if (getType() == Type.HELMET) return getEnergyStorage(stack).getStoredEnergy() < getEnergyStorage(stack).getMaxCapacity();
        return false;
    }

    @Override
    public void onArmorTick (ItemStack stack, Level level, Player player) {
        setDamage(stack, 0);
        if (player.isInFluidType()) bootsTick(player);
        if (player.isEyeInFluidType(Fluids.WATER.getFluidType())) {
            player.addEffect(
                new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 255, false, false, false)
            );
        }
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DivingGear chest && chest.getType() == Type.CHESTPLATE && getType() == Type.HELMET && level.getGameTime() % 40 == 0 && player.isEyeInFluidType(Fluids.WATER.getFluidType())) {
            var chestItem = player.getItemBySlot(EquipmentSlot.CHEST);
            var energyStorage = getEnergyStorage(stack);
            var oxygen = Math.min(320, Math.max(chest.getTankSize() - chest.getFluidAmount(chestItem), 0));
            var extract = Math.min(energyStorage.getStoredEnergy(), oxygen * 2);
            long insertOxygen = extract / 2;
            energyStorage.internalExtract(extract, false);
            var holder = new ItemStackHolder(chestItem);
            insert(holder, FluidHooks.newFluidHolder(chest.getFluid(holder.getStack()), insertOxygen, null));
        }
        if (this.getType() != Type.CHESTPLATE) return;
        var oxygen = getFluidAmount(stack);
        var consume = 0L;
        if (hasFullGear(player) && this.getType() == Type.CHESTPLATE && stack.getItem() instanceof DivingGear gear) {
            if (player.level().getGameTime() % 40 == 0 && gear.getFluidAmount(stack) <= 0) {
                player.hurt(player.damageSources().drown(), 2);
            }
            if (gear.getFluidAmount(stack) > 0 && player.level().getGameTime() % 40 == 0) {
                var holder = new ItemStackHolder(stack);
                extract(holder, FluidHooks.newFluidHolder(getFluid(holder.getStack()), 5, null));
            }
        }
        if (player.level().getGameTime() % 35 == 0 && oxygen > 1 && player.getAirSupply() < player.getMaxAirSupply()) {
            if ((player.getMaxAirSupply() - player.getAirSupply()) * 2L > oxygen) {
                player.setAirSupply((int) Math.min(oxygen / 2, player.getMaxAirSupply()));
                consume = oxygen;
            } else {
                consume = ((player.getMaxAirSupply() - player.getAirSupply()) * 2L) / 15;
                player.setAirSupply(player.getMaxAirSupply());
            }
            var holder = new ItemStackHolder(stack);
            extract(holder, FluidHooks.newFluidHolder(getFluid(holder.getStack()), consume, null));
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
            list.add(Component.translatable("tooltip.realisticmc.fluid_stored", GTMaterials.Oxygen.getLocalizedName(), getFluidAmount(stack), getTankSize()));
        if (this.getType() == Type.HELMET)
            list.add(Component.translatable("tooltip.realisticmc.energy_stored", getEnergyStorage(stack).getStoredEnergy(), getEnergyStorage(stack).getMaxCapacity()));
    }

    @Override
    public @Nullable String getArmorTexture (ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return ArmorUtils.getArmorTexture(name);
    }

}
