package team.starworld.realisticmc.content.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;
import team.starworld.realisticmc.registry.RMCRegistries;
import team.starworld.realisticmc.util.ArmorUtils;
import team.starworld.realisticmc.util.ModelUtils;

import java.util.UUID;
import java.util.function.Consumer;

public class DivingFlipper extends ArmorItem {

    public static final AttributeModifier SWIM_SPEED_ATTRIBUTE_MODIFIER = new AttributeModifier(
        UUID.fromString("9829243c-6a8a-4c45-ba9b-478dfa650525"),
        RMCRegistries.rl("diving_flipper_swim_speed_attribute_modifier").toString(),
        1.5, AttributeModifier.Operation.MULTIPLY_TOTAL
    );

    protected final ResourceLocation name;

    public DivingFlipper (ResourceLocation name, Properties properties) {
        super(ArmorMaterials.DIAMOND, Type.BOOTS, properties);
        this.name = name;
    }

    @Override
    public void onArmorTick (ItemStack stack, Level level, Player player) {
        stack.getOrCreateTag().putBoolean("unbreakable", true);
    }

    @Override
    public Multimap <Attribute, AttributeModifier> getAttributeModifiers (EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.FEET)
            return ImmutableMultimap.of(
                ForgeMod.SWIM_SPEED.get(), SWIM_SPEED_ATTRIBUTE_MODIFIER
            );
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void initializeClient (Consumer <IClientItemExtensions> consumer) {
        consumer.accept(ModelUtils.FullArmorRendering.INSTANCE);
    }

    @Override
    public @Nullable String getArmorTexture (ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return ArmorUtils.getArmorTexture(name);
    }

}
