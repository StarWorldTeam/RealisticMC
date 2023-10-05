package team.starworld.realisticmc.content.item.armor;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import earth.terrarium.ad_astra.common.item.FluidContainingItem;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.starworld.realisticmc.api.item.armor.RMCArmor;
import team.starworld.realisticmc.api.item.armor.model.ArmorFullModel;
import team.starworld.realisticmc.util.ArmorUtils;
import team.starworld.realisticmc.util.ItemStackUtils;
import team.starworld.realisticmc.util.MathUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Consumer;


public class DivingGear extends ArmorItem implements RMCArmor, FluidContainingItem {

    protected ResourceLocation name;

    public static final AttributeModifier SWIMMING_SPEED_MODIFIER = new AttributeModifier(UUID.fromString("89b46e4a-5feb-11ee-8c99-0242ac120002"), "diving_gear_swimming_speed", 3, AttributeModifier.Operation.MULTIPLY_BASE);

    public DivingGear (Type type, ResourceLocation name) {
        super(ArmorMaterials.DIAMOND, type, new Item.Properties().durability(Integer.MAX_VALUE));
        this.name = name;
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
        consumer.accept(Rendering.INSTANCE);
    }

    public static class Rendering implements IClientItemExtensions {

        public static final Rendering INSTANCE = new Rendering();

        private Rendering () {}


        @Override
        public @NotNull HumanoidModel <?> getHumanoidArmorModel (LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel <?> original) {
            return equipmentSlot == EquipmentSlot.LEGS || equipmentSlot == EquipmentSlot.FEET ? original : ArmorFullModel.INSTANCE.get();
        }

    }

    @Override
    public void inventoryTick (@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        ItemStackUtils.setUnbreakable(stack, true);
    }

    @Override
    public int getBarWidth (@NotNull ItemStack stack) {
        return Math.round(((float) getFluidAmount(stack) * 13f / (float) getTankSize()));
    }

    @Override
    public int getBarColor (@NotNull ItemStack stack) {
        return 0xA8D8F9;
    }

    @Override
    public boolean isBarVisible (@NotNull ItemStack stack) {
        return getFluidAmount(stack) != getTankSize();
    }

    @Override
    public Multimap <Attribute, AttributeModifier> getAttributeModifiers (EquipmentSlot slot, ItemStack stack) {
        if (this.getType() == Type.BOOTS && slot == EquipmentSlot.FEET) {
            return Multimaps.forMap(
                Map.of(
                    ForgeMod.SWIM_SPEED.get(), SWIMMING_SPEED_MODIFIER
                )
            );
        }
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void onArmorTick (ItemStack stack, Level level, Player player) {
        setDamage(stack, 0);
        if (!(player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof DivingGear) || !(this.getType() == Type.CHESTPLATE)) return;
        var oxygen = getFluidAmount(stack);
        var consume = 0L;
        if (hasFullGear(player) && hasBoots(player) && hasLeggings(player) && this.getType() == Type.CHESTPLATE && stack.getItem() instanceof DivingGear gear) {
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
    }

    @Override
    public @Nullable String getArmorTexture (ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return ArmorUtils.getArmorTexture(name);
    }

}
