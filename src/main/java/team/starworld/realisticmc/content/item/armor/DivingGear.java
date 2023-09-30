package team.starworld.realisticmc.content.item.armor;

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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.starworld.realisticmc.api.item.armor.RMCArmor;
import team.starworld.realisticmc.api.item.armor.model.ArmorFullModel;
import team.starworld.realisticmc.registry.RMCItems;
import team.starworld.realisticmc.util.ArmorUtils;
import team.starworld.realisticmc.util.ItemStackUtils;
import team.starworld.realisticmc.util.MathUtils;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;


public class DivingGear extends ArmorItem implements RMCArmor, FluidContainingItem {

    protected ResourceLocation name;

    public DivingGear (Type type, ResourceLocation name) {
        super(ArmorMaterials.DIAMOND, type, new Item.Properties().durability(Integer.MAX_VALUE));
        this.name = name;
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
    public void onArmorTick (ItemStack stack, Level level, Player player) {
        setDamage(stack, 0);
        if (!player.getItemBySlot(EquipmentSlot.HEAD).is(RMCItems.DIVING_MASK.get()) || !(this.getType() == Type.CHESTPLATE)) return;
        var oxygen = getFluidAmount(stack);
        var consume = 0L;
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
            if (holder.isDirty()) player.setItemSlot(EquipmentSlot.CHEST, holder.getStack());
        }

    }

    @Override
    public MathUtils.TypedFloatNumber getArmorWaterPressureResistant () {
        return new MathUtils.TypedFloatNumber(1, 1);
    }

    @Override
    public long getTankSize () {
        return this.getType() == Type.CHESTPLATE ? 64000 : 0;
    }

    @Override
    public BiPredicate <Integer, FluidHolder> getFilter () {
        return (ignored, holder) -> holder.is(ModTags.OXYGEN);
    }

    @Override
    public void appendHoverText (@NotNull ItemStack stack, @Nullable Level level, @NotNull List <Component> list, @NotNull TooltipFlag flag) {
        if (this.getType() == Type.CHESTPLATE)
            list.add(Component.translatable("tooltip.realisticmc.diving_suit_oxygen_stored", getFluidAmount(stack), getTankSize()));
    }

    @Override
    public @Nullable String getArmorTexture (ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return ArmorUtils.getArmorTexture(name);
    }

}
